<?php

namespace App\Controller;
use App\Service\Cart\CartService;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
class CartController extends AbstractController
{
    /**
     * @Route("/cart", name="app_cart")
     */
    public function index(CartService $cartService)
    {
        return $this->render('cart/index.html.twig', [
            'items'=>$cartService->getFullCart() ,
            'total'=>$cartService->getTotal()
            ]);
    }
    /**
     * @Route("/cart/add/{id}", name="cart_add")
     */
    public function add($id , CartService $cartservice)
    {
$cartservice->add($id);
   return $this->redirectToRoute("app_cart");
    }
    /**
     * @Route("/cart/remove/{id}", name="cart_remove")
     */
    public  function remove($id , CartService  $cartService){
$cartService->remove($id);
        return $this->redirectToRoute("app_cart");
    }
}
