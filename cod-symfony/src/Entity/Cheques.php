<?php

namespace App\Entity;

use App\Repository\ChequesRepository;
use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity(repositoryClass=ChequesRepository::class)
 */
class Cheques
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
    private $id_cheques;

    /**
     * @ORM\Column(type="float")
     */
    private $montant;

    /**
     * @ORM\ManyToOne(targetEntity=Compte::class, inversedBy="cheques")
     */
    private $proprietaire;

    /**
     * @ORM\Column(type="date")
     */
    private $date_cheque;

    /**
     * @ORM\Column(type="string", length=100)
     */
    private $lieu;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $signature;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getIdCheques(): ?int
    {
        return $this->id_cheques;
    }

    public function setIdCheques(int $id_cheques): self
    {
        $this->id_cheques = $id_cheques;

        return $this;
    }

    public function getMontant(): ?float
    {
        return $this->montant;
    }

    public function setMontant(float $montant): self
    {
        $this->montant = $montant;

        return $this;
    }

    public function getProprietaire(): ?Compte
    {
        return $this->proprietaire;
    }

    public function setProprietaire(?Compte $proprietaire): self
    {
        $this->proprietaire = $proprietaire;

        return $this;
    }

    public function getDateCheque(): ?\DateTimeInterface
    {
        return $this->date_cheque;
    }

    public function setDateCheque(\DateTimeInterface $date_cheque): self
    {
        $this->date_cheque = $date_cheque;

        return $this;
    }

    public function getLieu(): ?string
    {
        return $this->lieu;
    }

    public function setLieu(string $lieu): self
    {
        $this->lieu = $lieu;

        return $this;
    }

    public function getSignature(): ?string
    {
        return $this->signature;
    }

    public function setSignature(string $signature): self
    {
        $this->signature = $signature;

        return $this;
    }
}
