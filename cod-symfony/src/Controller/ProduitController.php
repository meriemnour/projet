<?php

namespace App\Controller;

use App\Entity\Menu;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class ProduitController extends AbstractController
{
    /**
     * @Route("/produit", name="app_produit")
     */
    public function index(): Response
    {
        $menus = $this->getDoctrine()->getManager()->getRepository(Menu::class)->findAll();
        return $this->render('produit/index.html.twig', ['m' => $menus]
        );
    }
}
