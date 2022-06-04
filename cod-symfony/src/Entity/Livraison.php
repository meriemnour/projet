<?php

namespace App\Entity;

use App\Repository\LivraisonRepository;
use Doctrine\ORM\Mapping as ORM;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Symfony\Component\Validator\Constraints as Assert;


/**
 * @ORM\Entity(repositoryClass=LivraisonRepository::class)
 */
class Livraison
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;


    /**
     *     @Assert\NotBlank(message="field can't be empty")
     * @Assert\Length(
     *      min = 5,
     *      minMessage="The minimum length is 5"
     *
     *     )
     * @ORM\Column(type="string", length=255)
     */
    private $nom_livraison;

    /**
     * @Assert\NotBlank(message="field can't be empty")
     * @Assert\Length(
     *      min = 5,
     *      minMessage="The minimum length is 5"
     *
     *     )
     * @ORM\Column(type="string", length=255)
     */
    private $date;

    /**
     * @Assert\NotBlank(message="field can't be empty")
     * @Assert\Length(
     *      min = 5,
     *      minMessage="The minimum length is 5"
     *
     *     )
     * @ORM\Column(type="string", length=255)
     */
    private $destination;

    /**
     * @var \Doctrine\Common\Collections\ArrayCollection
     * @ORM\ManyToOne(targetEntity=Vehicule::class, inversedBy="livraisons")
     */
    private $Veh;



    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNomLivraison(): ?string
    {
        return $this->nom_livraison;
    }

    public function setNomLivraison(string $nom_livraison): self
    {
        $this->nom_livraison = $nom_livraison;

        return $this;
    }

    public function getDate(): ?string
    {
        return $this->date;
    }

    public function setDate(string $date): self
    {
        $this->date = $date;

        return $this;
    }

    public function getDestination(): ?string
    {
        return $this->destination;
    }

    public function setDestination(string $destination): self
    {
        $this->destination = $destination;

        return $this;
    }

    public function getVeh(): ?Vehicule
    {
        return $this->Veh;
    }

    public function setVeh(?Vehicule $Veh): self
    {
        $this->Veh = $Veh;

        return $this;
    }
    public function __toString() {

        return (string) $this->destination;
    }


}
