<?php

namespace App\Entity;

use App\Repository\TransactionRepository;
use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity(repositoryClass=TransactionRepository::class)
 */
class Transaction
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
    private $id_transaction;

    /**
     * @ORM\ManyToOne(targetEntity=Compte::class, inversedBy="transactions")
     */
    private $RIB_emetteur;

    /**
     * @ORM\Column(type="string", length=150)
     */
    private $RIB_recepteur;

    /**
     * @ORM\Column(type="float")
     */
    private $montant_transaction;

    /**
     * @ORM\Column(type="datetime")
     */
    private $date_transaction;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $description_transaction;

    /**
     * @ORM\ManyToOne(targetEntity=Compte::class, inversedBy="transactions")
     */
    private $fullname_emetteur;

    /**
     * @ORM\Column(type="string", length=150)
     */
    private $fullname_recepteur;

    /**
     * @ORM\Column(type="string", length=100)
     */
    private $type_transaction;

    /**
     * @ORM\Column(type="integer")
     */
    private $etat_transaction;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getIdTransaction(): ?int
    {
        return $this->id_transaction;
    }

    public function setIdTransaction(int $id_transaction): self
    {
        $this->id_transaction = $id_transaction;

        return $this;
    }

    public function getRIBEmetteur(): ?Compte
    {
        return $this->RIB_emetteur;
    }

    public function setRIBEmetteur(?Compte $RIB_emetteur): self
    {
        $this->RIB_emetteur = $RIB_emetteur;

        return $this;
    }

    public function getRIBRecepteur(): ?string
    {
        return $this->RIB_recepteur;
    }

    public function setRIBRecepteur(string $RIB_recepteur): self
    {
        $this->RIB_recepteur = $RIB_recepteur;

        return $this;
    }

    public function getMontantTransaction(): ?float
    {
        return $this->montant_transaction;
    }

    public function setMontantTransaction(float $montant_transaction): self
    {
        $this->montant_transaction = $montant_transaction;

        return $this;
    }

    public function getDateTransaction(): ?\DateTimeInterface
    {
        return $this->date_transaction;
    }

    public function setDateTransaction(\DateTimeInterface $date_transaction): self
    {
        $this->date_transaction = $date_transaction;

        return $this;
    }

    public function getDescriptionTransaction(): ?string
    {
        return $this->description_transaction;
    }

    public function setDescriptionTransaction(string $description_transaction): self
    {
        $this->description_transaction = $description_transaction;

        return $this;
    }

    public function getFullnameEmetteur(): ?Compte
    {
        return $this->fullname_emetteur;
    }

    public function setFullnameEmetteur(?Compte $fullname_emetteur): self
    {
        $this->fullname_emetteur = $fullname_emetteur;

        return $this;
    }

    public function getFullnameRecepteur(): ?string
    {
        return $this->fullname_recepteur;
    }

    public function setFullnameRecepteur(string $fullname_recepteur): self
    {
        $this->fullname_recepteur = $fullname_recepteur;

        return $this;
    }

    public function getTypeTransaction(): ?string
    {
        return $this->type_transaction;
    }

    public function setTypeTransaction(string $type_transaction): self
    {
        $this->type_transaction = $type_transaction;

        return $this;
    }

    public function getEtatTransaction(): ?int
    {
        return $this->etat_transaction;
    }

    public function setEtatTransaction(int $etat_transaction): self
    {
        $this->etat_transaction = $etat_transaction;

        return $this;
    }
}
