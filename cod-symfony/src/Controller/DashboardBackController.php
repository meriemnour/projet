<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class DashboardBackController extends AbstractController
{
    /**
     * @Route("/", name="dashboard_back")
     */
    public function index(): Response
    {
        return $this->render('dashboard_back/index.html.twig', [
            'controller_name' => 'DashboardBackController',
        ]);
    }
}
