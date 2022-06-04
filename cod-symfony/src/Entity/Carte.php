<?php

namespace App\Entity;

use App\Repository\CarteRepository;
use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity(repositoryClass=CarteRepository::class)
 */
class Carte
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="integer")
     */
    private $id_carte;

    /**
     * @ORM\Column(type="string", length=100)
     */
    private $num_carte;

    /**
     * @ORM\Column(type="date")
     */
    private $date_ex;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $mp;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $login;

    /**
     * @ORM\OneToOne(targetEntity=Compte::class, cascade={"persist", "remove"})
     */
    private $id_client;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getIdCarte(): ?int
    {
        return $this->id_carte;
    }

    public function setIdCarte(int $id_carte): self
    {
        $this->id_carte = $id_carte;

        return $this;
    }

    public function getNumCarte(): ?string
    {
        return $this->num_carte;
    }

    public function setNumCarte(string $num_carte): self
    {
        $this->num_carte = $num_carte;

        return $this;
    }

    public function getDateEx(): ?\DateTimeInterface
    {
        return $this->date_ex;
    }

    public function setDateEx(\DateTimeInterface $date_ex): self
    {
        $this->date_ex = $date_ex;

        return $this;
    }

    public function getMp(): ?string
    {
        return $this->mp;
    }

    public function setMp(string $mp): self
    {
        $this->mp = $mp;

        return $this;
    }

    public function getLogin(): ?string
    {
        return $this->login;
    }

    public function setLogin(string $login): self
    {
        $this->login = $login;

        return $this;
    }

    public function getIdClient(): ?Compte
    {
        return $this->id_client;
    }

    public function setIdClient(?Compte $id_client): self
    {
        $this->id_client = $id_client;

        return $this;
    }
}
