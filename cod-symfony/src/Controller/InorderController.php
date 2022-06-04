<?php

namespace App\Controller;

use App\Entity\Inorder;
use App\Entity\Order;
use App\Repository\MenuRepository;
use App\Service\Cart\CartService;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Repository\UtilisateurRepository;

class InorderController extends AbstractController
{
    /**
     * @Route("/Inorder", name="create_Inorderr")
     */
    public function createProduct( CartService $cartService, MenuRepository $menuRepository ): Response
    {   $entityManager1 = $this->getDoctrine()->getManager();
        $order = new Order();
        $order->SetCustomerId(1);
        $order->setStatus("On Delevery");
        $date = new \DateTime('@'.strtotime('now'));
        $order->setTimeStamp( $date);
        $order->setTotal($cartService->getTotal());

        // tell Doctrine you want to (eventually) save the Product (no queries yet)
        $entityManager1->persist($order);

        // actually executes the queries (i.e. the INSERT query)
        $entityManager1->flush();
        // you can fetch the EntityManager via $this->getDoctrine()
        // or you can add an argument to the action: createProduct(EntityManagerInterface $entityManager)
        foreach ($cartService->getFullCart() as $item) {

            $entityManager = $this->getDoctrine()->getManager();

            $Inorder = new Inorder();
            $Inorder->setMenuId($item['menu']->getId());
            $Inorder->SetOrder($order);
            $Inorder->setQuantity($item['quantity']);

            // tell Doctrine you want to (eventually) save the Product (no queries yet)
            $entityManager->persist($Inorder);

            // actually executes the queries (i.e. the INSERT query)
            $entityManager->flush();




            // $this->addFlash('info4','excel file is in public !');
        }


        return $this->render('thanks/thanks.html.twig', []);
    }

    /**
     * @Route("/export",  name="export")
     */
    /*   public function export(CartService $cartService)
       {
           $spreadsheet = new Spreadsheet();

           $sheet = $spreadsheet->getActiveSheet();

           $sheet->setTitle('Product List');

           $sheet->getCell('A1')->setValue('id');
           $sheet->getCell('B1')->setValue('Menu Name');
           $sheet->getCell('C1')->setValue('Quantity');



           // Increase row cursor after header write
           $sheet->fromArray($cartService->getFullCart(),null, 'A2', true);


           $writer = new Xlsx($spreadsheet);

           $writer->save('helloworld.xlsx');

          // $this->addFlash('info4','excel file is in public !');
           return $this->redirectToRoute("reservationlist");
       }

   */
}
