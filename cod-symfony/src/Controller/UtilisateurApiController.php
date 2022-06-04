<?php

namespace App\Controller;

use App\Entity\Utilisateur;
use App\Repository\UtilisateurRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
//



class UtilisateurApiController extends AbstractController
{
    /**
     * @Route("/utilisateur/ajouterU", name="ajouterUtilisateurMobile")
     */
    public function ajouterUtilisateurMobile(Request $request ,UserPasswordEncoderInterface $passwordEncoder)
    {
        $em = $this->getDoctrine()->getManager();

        $cin_u = $request->get("cin_u");
        $nom_u = $request->get("nom_u");
        $prenom_u = $request->get("prenom_u");
        //$date_naissance = new \DateTime('@' . strtotime('now'));
        $email_u = $request->get("email_u");
        $num_tel = $request->get("num_tel");
        $role = $request->get("role");
        $mot_de_passe = $request->get("mot_de_passe");

        if(!filter_var($email_u, FILTER_VALIDATE_EMAIL)){
            return new Response("email invalid");
        }

        $utilisateur = new Utilisateur();
        $utilisateur->setCinU($cin_u);
        $utilisateur->setNomU($nom_u);
        $utilisateur->setPrenomU($prenom_u);
       // $utilisateur->setDateNaissance($date_naissance);
        $utilisateur->setEmailU($email_u);
        $utilisateur->setNumTel($num_tel);
        $utilisateur->setRole($role);
        $utilisateur->setMotDePasse($passwordEncoder->encodePassword(
            $utilisateur,
            $mot_de_passe
        ));

        $em->persist($utilisateur);
        $em->flush();

        $encoder = new JsonEncoder();
        $normalizer = new ObjectNormalizer();
        $normalizer->setCircularReferenceHandler(function ($object) {
            return $object->getId();
        });

        $serializer = new Serializer([$normalizer], [$encoder]);
        $formatted = $serializer->normalize($utilisateur);

        return new JsonResponse($formatted);


    }

    /**
     * @Route("/utilisateur/modifierU/{id}", name="modifierUtilisateurMobile")
     */
    public function modifierUtilisateurMobile(Request $request, $id)
    {

        $em = $this->getDoctrine()->getManager();
//        $id = $request->get("id");
        $utilisateur = $em->getRepository(Utilisateur::class)->find($id);

        $cin_u = $request->get("cin");
        $nom_u = $request->get("nom");
        $prenom_u = $request->get("prenom");
        //$date_naissance = new \DateTime('@' . strtotime('now'));
        $email_u = $request->get("email");
        $num_tel = $request->get("tel");
        $role = $request->get("role");
        $mot_de_passe = $request->get("mdp");



        $utilisateur->setCinU($cin_u);
        $utilisateur->setNomU($nom_u);
        $utilisateur->setPrenomU($prenom_u);
        //$utilisateur->setDateNaissance($date_naissance);
        $utilisateur->setEmailU($email_u);
        $utilisateur->setNumTel($num_tel);
        $utilisateur->setRole($role);
       // $utilisateur->setMotDePasse($mot_de_passe);

        $em->persist($utilisateur);
        $em->flush();

        //RESPONSE JSON FROM OUR SERVER
        $encoder = new JsonEncoder();
        $normalizer = new ObjectNormalizer();
        $normalizer->setCircularReferenceHandler(function ($object) {
            return $object->getId();
        });

        //  $serializer = new Serializer([$normalizer],[$encoder]);
        //$formatted = $serializer->normalize($prod);

        return new JsonResponse("Votre profile a été modifié avec succès !");
    }

    /**
     * @Route("/utilisateur/supprimerU/{id}", name="supprimerUtilisateurMobile")
     */
    public function supprimerUtilisateurMobile($id)
    {
        $em = $this->getDoctrine()->getManager();
        $prod = $em->getRepository(Utilisateur::class)->find($id);
        $em->remove($prod);
        $em->flush();
        return new JsonResponse("Le utilisateur a bien été supprimé !");

    }

    /**
     * @Route("/utilisateur/afficherU", name="afficherUtilisateurMobile")
     */
    public function afficherUtilisateurMobile()
    {
        $em = $this->getDoctrine()->getManager();
        $utilisateur= $em->getRepository(Utilisateur::class)->findAll();

        //RESPONSE JSON FROM OUR SERVER
        $encoder = new JsonEncoder();
        $normalizer = new ObjectNormalizer();

        //JOIN ERROR
        $normalizer->setCircularReferenceLimit(1);
        $normalizer->setCircularReferenceHandler(function ($object) {
            if (method_exists($object, 'getId')) {
                return $object->getId();
            }
        });

        $serializer = new Serializer([$normalizer], [$encoder]);
        $formatted = $serializer->normalize($utilisateur);

        return new JsonResponse($formatted);
    }

    /**
     * @Route("/login/mobile", name="api_login", methods={"POST"})
     */
    public function api_login(NormalizerInterface $normalizable, UtilisateurRepository $userRepository, Request $request, UserPasswordEncoderInterface $passwordHasher)
    {
        //$test = $request->query->get("username");

        $user = $userRepository->findOneBy(['email_u' => $request->get('email_u')]);


        if ($user) {
            $result = $passwordHasher->isPasswordValid($user, $request->get('mot_de_passe'));
            if ($result) {

                $jsonContent = $normalizable->normalize($user, 'json', ['groups' => 'post:read']);
                return new Response(json_encode( "invalid informations"));

            }
        }
        return new JsonResponse([
            'error' => "invalid informations"
        ]);
    }


    /**
     * @Route("user/all/users", name="users_mobile", methods={"GET"})
     */
    public function mobile_all_users(NormalizerInterface $normalizable, UtilisateurRepository $userRepository, Request $request)
    {
        $users = $userRepository->findAll();
        $jsonContent = $normalizable->normalize($users, 'json', ['groups' => 'post:read']);
        return new Response(json_encode($jsonContent));
    }



    /**
     * @Route("/signup/mobile", name="api_signup", methods={"POST"})
     */
    public function api_signup(UserPasswordEncoderInterface $userPasswordEncoder, NormalizerInterface $normalizable, EntityManagerInterface $entityManager, Request $request, UserPasswordEncoderInterface $passwordEncoder)
    {
        //$users = $userRepository->findAll();
       // $user = new User();
        $utilisateur = new Utilisateur();



        //

        $cin_u = $request->get("cin_u");
        $nom_u = $request->get("nom_u");
        $prenom_u = $request->get("prenom_u");
        //$date_naissance = new \DateTime('@' . strtotime('now'));
        $email_u = $request->get("email_u");
        $num_tel = $request->get("num_tel");
        //$role = $request->get("role");
        $mot_de_passe = $request->get("mot_de_passe");


//

        $utilisateur->setCinU($cin_u);
        $utilisateur->setNomU($nom_u);
        $utilisateur->setPrenomU($prenom_u);
        // $utilisateur->setDateNaissance($date_naissance);
        $utilisateur->setEmailU($email_u);
        $utilisateur->setNumTel($num_tel);
        $utilisateur->setRole("ROLE_USER");
       // $utilisateur->setMotDePasse($mot_de_passe);

        $utilisateur->setMotDePasse($passwordEncoder->encodePassword(
            $utilisateur,
            $mot_de_passe
        ));


       /* $user->setPassword(
            $userPasswordEncoder->encodePassword(
                $user,
                $request->get('password')
            )
        );*/
        $current_date = new \DateTime('@' . strtotime('+01:00'));
        $utilisateur->setDateNaissance($current_date);
       // $utilisateur->setRoles("ROLE_USER");

       // $bytes = random_bytes(3);
       // $verificationCode = bin2hex($bytes);
       // $utilisateur->SetVerificationCode($verificationCode);




        /* $this->twilio->messages->create("+216" . $user->getNumTel(), [
            'from' => $this->fromNumber,
            'body' => "To Activate Your account please use this code upon logging in \n Code :$verificationCode"
        ]); */
       // $user->setIsVerified(1);
        $entityManager->persist($utilisateur);
        $entityManager->flush();
        return new JsonResponse([
            'success' => "user has been added"
        ]);
    }



}
