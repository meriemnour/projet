<?php

namespace App\Entity;

use App\Repository\InorderRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Serializer\Annotation\Groups;

/**
 * @ORM\Entity(repositoryClass=InorderRepository::class)
 */
class Inorder
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     * @Groups("Inorder")
     */
    private $id;

    /**
     * @ORM\Column(type="integer")
     * @Groups("Inorder")
     */
    private $menu_id;

    /**
     * @ORM\ManyToOne(targetEntity="App\Entity\Order",inversedBy="inorder")
     * @Groups("Inorder")
     */

    private $order;

    /**
     * @ORM\Column(type="integer")
     * @Groups("Inorder")
     */
    private $quantity;


    public function getId(): ?int
    {
        return $this->id;
    }

    public function getMenuId(): ?int
    {
        return $this->menu_id;
    }

    public function setMenuId(int $menu_id): self
    {
        $this->menu_id = $menu_id;

        return $this;
    }

    public function getOrder(): ?Order
    {
        return $this->order;
    }

    public function setOrder(?Order $order): self
    {
        $this->order = $order;

        return $this;
    }

    public function getQuantity(): ?int
    {
        return $this->quantity;
    }

    public function setQuantity(int $quantity): self
    {
        $this->quantity = $quantity;

        return $this;
    }
}
