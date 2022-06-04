<?php

namespace App\Entity;

use App\Repository\OperationRepository;
use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity(repositoryClass=OperationRepository::class)
 */
class Operation
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
    private $id_operation;

    /**
     * @ORM\Column(type="float")
     */
    private $montant;

    /**
     * @ORM\Column(type="date")
     */
    private $date_operation;

    /**
     * @ORM\Column(type="string", length=100)
     */
    private $typeC;

    /**
     * @ORM\Column(type="float")
     */
    private $depense_hebdomadaire;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getIdOperation(): ?int
    {
        return $this->id_operation;
    }

    public function setIdOperation(int $id_operation): self
    {
        $this->id_operation = $id_operation;

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

    public function getDateOperation(): ?\DateTimeInterface
    {
        return $this->date_operation;
    }

    public function setDateOperation(\DateTimeInterface $date_operation): self
    {
        $this->date_operation = $date_operation;

        return $this;
    }

    public function getTypeC(): ?string
    {
        return $this->typeC;
    }

    public function setTypeC(string $typeC): self
    {
        $this->typeC = $typeC;

        return $this;
    }

    public function getDepenseHebdomadaire(): ?float
    {
        return $this->depense_hebdomadaire;
    }

    public function setDepenseHebdomadaire(float $depense_hebdomadaire): self
    {
        $this->depense_hebdomadaire = $depense_hebdomadaire;

        return $this;
    }
}
