<?php
namespace  App\Service\Cart ;
use App\Repository\MenuRepository;
use Symfony\Component\HttpFoundation\Session\SessionInterface;

class CartService
{
    protected $menuRepository;
    protected $session;

    public function __construct(SessionInterface $session, MenuRepository $menuRepository)
    {
        $this->session = $session;
        $this->menuRepository = $menuRepository;
    }

    public function add(int $id)
    {
        $panier = $this->session->get('panier', []);
        if (!empty($panier[$id])) {
            $panier[$id]++;
        } else {
            $panier[$id] = 1;
        }
        $this->session->set('panier', $panier);

    }

    public function remove(int $id)
    {
        $panier = $this->session->get('panier', []);
        if (!empty($panier[$id])) {
            unset($panier[$id]);
        }
        $this->session->set('panier', $panier);
    }

    public function getFullCart(): array
    {
        $panier = $this->session->get('panier', []);
        $panierwithdata = [];
        foreach ($panier as $id => $quantity) {
            $panierwithdata[] = [
                'menu' => $this->menuRepository->find($id),
                'quantity' => $quantity

            ];
        }
        return $panierwithdata;
    }

    public function getTotal(): float
    {
        $total = 0;
        foreach ($this->getFullCart() as $item) {
            $total += $item['menu']->getPrice()  * $item['quantity'];
        }
        return $total;

    }
}