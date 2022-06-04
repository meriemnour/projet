<?php

namespace App\Entity;

use App\Repository\ChequierRepository;
use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity(repositoryClass=ChequierRepository::class)
 */
class Chequier
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
    private $id_chequier;

    /**
     * @ORM\Column(type="date")
     */
    private $date_creation;

    /**
     * @ORM\ManyToOne(targetEntity=Compte::class, inversedBy="chequiers")
     */
    private $num_compte;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $motif_chequier;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getIdChequier(): ?int
    {
        return $this->id_chequier;
    }

    public function setIdChequier(int $id_chequier): self
    {
        $this->id_chequier = $id_chequier;

        return $this;
    }

    public function getDateCreation(): ?\DateTimeInterface
    {
        return $this->date_creation;
    }

    public function setDateCreation(\DateTimeInterface $date_creation): self
    {
        $this->date_creation = $date_creation;

        return $this;
    }

    public function getNumCompte(): ?Compte
    {
        return $this->num_compte;
    }

    public function setNumCompte(?Compte $num_compte): self
    {
        $this->num_compte = $num_compte;

        return $this;
    }

    public function getMotifChequier(): ?string
    {
        return $this->motif_chequier;
    }

    public function setMotifChequier(string $motif_chequier): self
    {
        $this->motif_chequier = $motif_chequier;

        return $this;
    }
}
