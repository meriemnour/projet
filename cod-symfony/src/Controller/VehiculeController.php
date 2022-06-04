<?php

namespace App\Controller;

use App\Entity\Vehicule;
use App\Form\VehiculeType;
use App\Repository\VehiculeRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;


/**
 * @Route("/vehicule")
 */
class VehiculeController extends AbstractController
{
    /**
     * @Route("/", name="app_vehicule_index", methods={"GET"})
     */
    public function index( Request $request, PaginatorInterface $paginator): Response
    { $donnees = $this->getDoctrine()->getRepository(Vehicule::class)->findAll();
        $vehicules = $paginator->paginate(
            $donnees,
            $request->query->getInt('page',1),
            4
        );
        return $this->render('vehicule/index.html.twig', [
            'vehicules' => $vehicules
        ]);
    }
    /**
     * @Route("/new", name="app_vehicule_new", methods={"GET", "POST"})
     */
    public function new(Request $request, VehiculeRepository $vehiculeRepository): Response
    {
        $vehicule = new Vehicule();
        $form = $this->createForm(VehiculeType::class, $vehicule);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $vehiculeRepository->add($vehicule);
            return $this->redirectToRoute('app_vehicule_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('vehicule/new.html.twig', [
            'vehicule' => $vehicule,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="app_vehicule_show", methods={"GET"})
     */
    public function show(Vehicule $vehicule): Response
    {
        return $this->render('vehicule/show.html.twig', [
            'vehicule' => $vehicule,
        ]);
    }

    /**
     * @Route("/{id}/edit", name="app_vehicule_edit", methods={"GET", "POST"})
     */
    public function edit(Request $request, Vehicule $vehicule, VehiculeRepository $vehiculeRepository): Response
    {
        $form = $this->createForm(VehiculeType::class, $vehicule);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $vehiculeRepository->add($vehicule);
            return $this->redirectToRoute('app_vehicule_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('vehicule/edit.html.twig', [
            'vehicule' => $vehicule,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="app_vehicule_delete", methods={"POST"})
     */
    public function delete(Request $request, Vehicule $vehicule, VehiculeRepository $vehiculeRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$vehicule->getId(), $request->request->get('_token'))) {
            $vehiculeRepository->remove($vehicule);
        }

        return $this->redirectToRoute('app_vehicule_index', [], Response::HTTP_SEE_OTHER);
    }



/**
* @Route("/mobile/vehicule", name="app_mobile")
*/
    public function majdi()
    {
        $produits = $this->getDoctrine()->getRepository(Vehicule::class)->findAll();
        $jsonData = array();
        $idx = 0;
        foreach($produits as $produit) {
            $temp = array(
                'idv' => $produit->getId(),
                'num' => $produit->getNumChassis(),

            );
            $jsonData[$idx++] = $temp;
        }



        return new Response(json_encode($jsonData));


    }
    /**
     * @Route("/mobile/newvehicule", name="mobile_produit_new", methods={"POST","GET"})
     */
    public function bardi(Request $request,  VehiculeRepository $vehiculeRepository): Response
    {
        $produit = new Vehicule();
        $num_chassis=$request->query->get('num_chassis');
        $type_vehicule=$request->query->get('type_vehicule');
        $num_immatriculation=$request->query->get('num_immatriculation');

        $produit->setNumChassis($num_chassis);
        $produit->setTypeVehicule($type_vehicule);
        $produit->setNumImmatriculation($num_immatriculation);

        $vehiculeRepository->add($produit);
        $temp = array(
            'idv' => $produit->getId(),
            'num_chassis' => $produit->getNumChassis(),
            'type_vehicule'=>$produit->getTypeVehicule(),
            'num_immatriculation' => $produit->getNumImmatriculation(),

        );

        return new Response(json_encode($temp));

    }
        /**
     * @Route("/mobile/updatevehicule/{id}", name="mobile_produit_update", methods={"POST","GET"})
     */
    public function update(Request $request,  VehiculeRepository $vehiculeRepository,$id): Response
    {
        $produit = $this->getDoctrine()->getRepository(Vehicule::class)->find($id);
        $num_chassis=$request->query->get('num_chassis');
        $type_vehicule=$request->query->get('type_vehicule');
        $num_immatriculation=$request->query->get('num_immatriculation');

        $produit->setNumChassis($num_chassis);
        $produit->setTypeVehicule($type_vehicule);
        $produit->setNumImmatriculation($num_immatriculation);

        $vehiculeRepository->add($produit);
        $temp = array(
            'idv' => $produit->getId(),
            'num_chassis' => $produit->getNumChassis(),
            'type_vehicule'=>$produit->getTypeVehicule(),
            'num_immatriculation' => $produit->getNumImmatriculation(),

        );

        return new Response(json_encode($temp));

    }
      /**
     * @Route("/delprod/{id}", name="delprod")
     */
    public function delVehicule(Request $request,NormalizerInterface $normalizer,$id)
    {
        $em=$this->getDoctrine()->getManager();
        $menu=$this->getDoctrine()->getRepository(Vehicule::class)
            ->find($request->get('id'));
        $em->remove($menu);
        $em->flush();
        $jsonContent = $normalizer->normalize($menu,'json',['groups'=>'menu']);
        return new Response(json_encode($jsonContent));
    }





}
