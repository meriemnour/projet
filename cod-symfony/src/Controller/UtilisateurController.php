<?php

namespace App\Controller;

use App\Entity\Utilisateur;
use App\Form\EditProfileType;
use App\Form\ModiferUType;
use App\Form\ModifierUBackType;
use App\Form\ResetPassType;
use App\Form\UtilisateurBackType;
use App\Form\UtilisateurType;
use App\Repository\UtilisateurRepository;
use http\Client\Curl\User;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Serializer;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\Routing\Generator\UrlGeneratorInterface;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\IsGranted;
use Symfony\Component\Security\Csrf\TokenGenerator\TokenGeneratorInterface;
use Twilio\Rest\Client;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\HttpFoundation\JsonResponse;


class UtilisateurController extends AbstractController
{
    /**
     * @Route("/inscription", name="inscription")
     */
    public function inscription(Request $request , UserPasswordEncoderInterface $encoder ,\Swift_Mailer $mailer, EntityManagerInterface $entityManager) : Response{
        $Utilisateur = new Utilisateur();
        $form=$this->createForm(UtilisateurType::class,$Utilisateur);
        $form->handleRequest($request);
        if($form->isSubmitted()&& $form->isValid()){
            $hash = $encoder->encodePassword($Utilisateur, $Utilisateur->getMotDePasse());
            $Utilisateur->setMotDePasse($hash);
            //on gere le token d'activation -> lors de l'inscription le user avoir automatiquement un token aletoire unique
            $Utilisateur->setActivationToken(md5(uniqid()));

            $Utilisateur=$form->getData();
            $em=$this->getDoctrine()->getManager();
            $em->persist($Utilisateur);
            $em->flush();

            //o cree un msg
            $message =(new \Swift_Message('Activation de votre compte'))
                //on attribut l'expediteur
                ->setFrom('foodyesprit@gmail.com')
                //on atribut le destinataire
                ->setTo($Utilisateur->getEmailU())
                // on met le contenu
                ->setBody(
                    $this->renderView(
                        'emails/activation.html.twig', ['token' => $Utilisateur->getActivationToken()]
                    ),
                    'text/html'
                );
            //on envoie le mail
            $mailer->send($message);
            $this->addFlash('message','Un email de verification de compte a ete envoyee');

            /*------------------------------Twilio------------------------------------
            $entityManager->persist($Utilisateur);
            $entityManager->flush();
           
 
            $sid    = "AC73de591a7c715cbbab9070219267708c"; 
            $token  = "33f81b63854a098375104ea7f474d716"; 
            $twilio = new Client($sid, $token); 
            $message1 = $twilio->messages 
                      ->create("+21652778549", // to 
                               array(  
                                   "messagingServiceSid" => "MGcb5e3ea853dc424b7debba96125d5275",      
                                   "body" => "hello" 
                               ) 
                      ); 
                print($message1->sid);
           
            ------------------------------------------------------------------*/

            return $this->redirectToRoute('login');
            }
        return $this->render('utilisateur/inscription.html.twig',[
            'formInscription'=>$form->createView()]);
    }

    /**
     * @Route ("/activation/{token}",name="activation")
     */
    public function activation($token,UtilisateurRepository $utilisateurRep){
        //on verfie si un utilisateur a un token
        $utilisateur = $utilisateurRep->findOneBy(['activation_token' => $token]);
        //si aucun utilisateur existe avec ce token
        if (! $utilisateur)
        {
            //error 404
            throw $this->createNotFoundException('cet utilisateur n\'existe pas');
        }
        //on supprime le token
        $utilisateur->setActivationToken(null);
        $enityManager = $this->getDoctrine()->getManager();
        $enityManager->persist($utilisateur);
        $enityManager->flush();
        //on envoie un msg flash
        $this->addFlash('message','vous avez bien activer votre compte');
        //retourne a le profile
        return $this->redirectToRoute('login');
    }

    /**
     * @Route ("/login" , name="login")
     */
    public function login(){
        /*$utilisateur = $this->getUser() ;
        if(!$utilisateur)
        {
            $this->addFlash('message','Merci de vous connecter');
            return $this->redirectToRoute('inscription');
        }*/

        return $this->render('utilisateur/login.html.twig');
    }

    /**
     * @Route ("/logout" , name="logout")
     */
    public function logout(){}

    /**
     * @Route ("/base_front_connecte" , name="base_front_connecte")
     */

    public function base_front_connecte(){
        return $this->render('base_front_connecte.html.twig');
    }

    /**
     * @Route ("/profile" , name="profile")
     */

    public function profile(){
        return $this->render('utilisateur/profile.html.twig');
    }


    /**
     * @Route("/editProfile", name="editProfile")
     */
    public function editProfile(Request $request): Response
    {
        //Récupérer le classroom à supprimer
        $Utilisateur = $this->getUser();

        // Construction du formulaire
        $form=$this->createForm(EditProfileType::class, $Utilisateur);

        //recuperer les donnees saisies
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid())
        {
            $em=$this->getDoctrine()->getManager();

            $em->flush();

            $this->addFlash('message','profile mis a jour avec succès');
            return $this->redirectToRoute('profile');
        }
        return $this->render('utilisateur/editProfile.html.twig', [
            //'controller_name' => 'ClassroomController',
            'formEdit'=> $form->createView()
        ]);
    }


    /**
     * @Route("/editPass", name="editPass")
     */
    public function editPass(Request $request , UserPasswordEncoderInterface $passwordEncoder){
        if ($request->isMethod('post')){
            $em= $this->getDoctrine()->getManager();
            $utilisateur = $this->getUser();

            if ($request->request->get('pass1') == $request->request->get('pass2')){
                $utilisateur->setMotDePasse($passwordEncoder->encodePassword($utilisateur,$request->request->get('pass1')));
                $em->flush();
                $this->addFlash('message','mot de passe mis a jour avec succes');

                return $this->redirectToRoute('profile');
            }else{
                $this->addFlash('error','Les deux mot de passe ne sont pas identiques');
            }
        }
        return $this->render('utilisateur/editPass.html.twig');
    }

    /**
     * @Route ("/mdpOubliee" , name = "mdpOubliee")
     */
    public function forgottenPass(Request $request , UtilisateurRepository $utilisateurRepo, \Swift_Mailer $mailer,
    TokenGeneratorInterface $tokenGenerator){
        //on cree le formulaire
        $form = $this->createForm(ResetPassType::class);
        //on fait le traitement
        $form->handleRequest($request);
        //si le formulaire est valide
        if ($form->isSubmitted() && $form->isValid()){
            //on recupere les donnees
            $donnees = $form->getData();
            //on cherche si l'utilisateur a cet email
            $Utilisateur = $utilisateurRepo->findOneBy(array('email_u' => $donnees));
            //si l'utilisateur n'existe pas
            if (! $Utilisateur){
                //on envoie un msg flash
                $this->addFlash('danger','cette adresse n\'existe pas');
                $this->redirectToRoute('login');
            }
            $token = $tokenGenerator->generateToken();
            try {
                $Utilisateur->setResetToken($token);
                $entityManager = $this->getDoctrine()->getManager();
                $entityManager->persist($Utilisateur);
                $entityManager->flush();

            }catch (\Exception $e){
                $this->addFlash('warning','une erreur est survenue :' .$e->getMessage() );
                return $this->redirectToRoute('login');
            }

            //on genere l url de reinisialisation de mdp
            $url = $this->generateUrl('resetPassword', ['token' => $token],
            UrlGeneratorInterface::ABSOLUTE_URL);
            // on envoie le message
            $message =(new \Swift_Message('Mot de passe oublié'))
                //on attribut l'expediteur
                ->setFrom('testutilisateurs1@gmail.com')
                //on atribut le destinataire
                ->setTo($Utilisateur->getEmailU())
                // on met le contenu
                ->setBody(
                    "<p>Bonjour,</p><p>Une demande de reinitialisation de mot de passe a ete effectuee pour le
                        site FoodX . Veuiller cliquer sur le lien suivant : " .$url . '</p>',
                    'text/html'

                );
            //on envoie le mail
            $mailer->send($message);
            //on cree le msg flash
            $this->addFlash('message','Un email de reinitialisation de mot de passe a ete envoyee');
            return $this->redirectToRoute('login');

        }
        //on envoie vers la page de demande de l email
        return $this->render('utilisateur/motDePasseOubliee.html.twig',['emailForm'=> $form->createView()]);
    }

    /**
     * @Route ("/resetPassword/{token}" ,name = "resetPassword")
     */
    public function resetPassword($token,Request $request, UserPasswordEncoderInterface $passwordEncoder){
        //chercher l'utilisateur avec le token fourni
        $Utilisateur= $this->getDoctrine()->getRepository(Utilisateur::class)->findOneBy(['reset_token' => $token]);
        if (! $Utilisateur){
            $this->addFlash('danger', 'Token inconnu');
            return $this->redirectToRoute('login');
        }
        //si le formulaire est envoee avec methode post
        if ($request->isMethod('POST')){
            //on supprime le token
            $Utilisateur->setResetToken(null);
            //on chiffre le mdp
            $Utilisateur->setMotDePasse($passwordEncoder->encodePassword($Utilisateur,$request->request->get('password')));
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($Utilisateur);
            $entityManager->flush();

            $this->addFlash('message','mot de passe bien modifier');
            return $this->redirectToRoute('login');
        }else{
            return  $this->render('utilisateur/resetPassword.html.twig',['token' => $token]);
        }
    }

    /**
     * @Route ("/check" , name="check")
     */
    public function check()
    {
        $utilisateur = $this->getUser();
        if (($utilisateur->getBloquerToken() == null) & ($utilisateur->getActivationToken() == null)) {
            if (in_array('ROLE_ADMIN', $utilisateur->getRoles())) {
                return $this->redirectToRoute('admin');
            } elseif (in_array('ROLE_EMPLOYEE', $utilisateur->getRoles())) {
                return $this->redirectToRoute('admin');
            } elseif (in_array('ROLE_USER', $utilisateur->getRoles())) {
                return $this->redirectToRoute('base_front_connecte');
            }
            return $this->redirectToRoute('login');
        }

        return $this->redirectToRoute('logout');
    }
/**
     * @Route("/loginUser/json",name="LOGINUserJSON")
     */
    public function LoginUserJSON(Request $request,UserPasswordEncoderInterface $userPasswordEncoder,NormalizerInterface $Normalizer)
    {
      
        $email= $request->query->get('email_u');
        $password= $request->query->get('mot_de_passe');
       
        $em=$this->getDoctrine()->getManager();
        $user= $em->getRepository(Utilisateur::class)->findOneBy(['email_u'=>$email]);
        //dd($email,$password);
        if($user){

            if (password_verify($password, $user->getPassword())) {
                $serializer = new \Symfony\Component\Serializer\Serializer([new ObjectNormalizer()]);
                $formatted = $Normalizer->normalize($user, 'json', ['groups' => 'post:read']);
                return new JsonResponse($formatted);
            } else {
                return new Response("password not found");
            }
        }else{
            return new Response("user not found");
        }
                
            
       
          
        
      

    }

}
