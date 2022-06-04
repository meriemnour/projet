<?php

namespace App\Controller;
use App\Entity\Menu;
use App\Repository\MenuRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;

class MobileController extends AbstractController
{
    /**
     * @Route("/mobile", name="app_mobile")
     */
    public function index(): Response
    {
        return $this->render('mobile/index.html.twig', [
            'controller_name' => 'MobileController',
        ]);
    }

    /**
     * @Route("/ListMenumobile", name="listmenumobile")
     */

    public function getReservation(Request $request,NormalizerInterface $normalizer  ):Response
    {
        $repository=$this->getDoctrine()->getRepository(Menu::class);
        $Reservation=$repository->findAll();
        $jsonContent = $normalizer->normalize($Reservation,'json',['groups'=>'Menu']);
        return new Response(json_encode($jsonContent));
    }
    /**
     * @Route("/addmenumobile", name="addmenumobile")
     */

    public function addreservation(Request $request, NormalizerInterface $normalizer)
    {

        $menu = new Menu();
        $menu->setName($request->get("name"));
        $menu->setPrice($request->get("price"));
        $menu->setCategory($request->get("category"));
        $menu->setDescription($request->get("description"));

        $em = $this->getDoctrine()->getManager();
        $em->persist($menu);
        $em->flush();

        $jsonContent = $normalizer->normalize($menu,'json',['groups'=>'menu']);
        return new Response("Menu added Successfully ".json_encode($jsonContent));
    }


    /**
     * @Route("/updatemenumobile",name="updatemenumobile")
     */
    public function updatereservation(Request $request,NormalizerInterface $normalizer)
    {   $id = $request->get("id");
        $menu = $this->getDoctrine()->getRepository(Menu::class)->find($id);
        $menu->setName($request->get("name"));
        $menu->setPrice($request->get("price"));
        $menu->setCategory($request->get("category"));
        $menu->setDescription($request->get("description"));
        $em = $this->getDoctrine()->getManager();
        $em->flush();
        $jsonContent = $normalizer->normalize($menu, 'json', ['groups' => 'menu']);

        return new Response("Menu updated Successfully ".json_encode($jsonContent));
    }
    /**
     * @Route("/deletemenumobile/{id}", name="deletemenumobile")
     */
    public function delreservation(Request $request,NormalizerInterface $normalizer,$id):Response
    {


        $em= $this->getDoctrine()->getManager();
        $reservation=$em->getRepository(Menu::class)->find($id);
        $em->remove($reservation);
        $em->flush();
        $jsonContent = $normalizer->normalize($reservation,'json',['groups'=>'menu']);
        return new Response("Menu deleted Successfully ".json_encode($jsonContent));
    }

}
