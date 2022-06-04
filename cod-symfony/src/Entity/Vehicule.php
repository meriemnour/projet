<?php

namespace App\Entity;

use App\Repository\VehiculeRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * @ORM\Entity(repositoryClass=VehiculeRepository::class)
 */
class Vehicule
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @Assert\NotBlank(message="field can't be empty")
     * @Assert\Length(
     *      min = 5,
     *      minMessage="The minimum length is 5"
     *
     *     )
     * @ORM\Column(type="string", length=255)
     */
    private $type_vehicule;

    /**
     * @Assert\NotBlank(message="field can't be empty")
     * @Assert\Length(
     *      min = 5,
     *      minMessage="The minimum length is 5"
     *
     *     )
     * @ORM\Column(type="string", length=255)
     */
    private $num_chassis;

    /**
     * @Assert\NotBlank(message="field can't be empty")
     * @Assert\Length(
     *      min = 5,
     *      minMessage="The minimum length is 5"
     *
     *     )
     * @ORM\Column(type="string", length=255)
     */
    private $num_immatriculation;

    /**
     * @var \Doctrine\Common\Collections\ArrayCollection
     * @ORM\OneToMany(targetEntity=Livraison::class, mappedBy="Veh")
     */
    private $livraisons;


    public function __construct()
    {
        $this->livraisons = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getTypeVehicule(): ?string
    {
        return $this->type_vehicule;
    }

    public function setTypeVehicule(string $type_vehicule): self
    {
        $this->type_vehicule = $type_vehicule;

        return $this;
    }

    public function getNumChassis(): ?string
    {
        return $this->num_chassis;
    }

    public function setNumChassis(string $num_chassis): self
    {
        $this->num_chassis = $num_chassis;

        return $this;
    }

    public function getNumImmatriculation(): ?string
    {
        return $this->num_immatriculation;
    }

    public function setNumImmatriculation(string $num_immatriculation): self
    {
        $this->num_immatriculation = $num_immatriculation;

        return $this;
    }

    /**
     * @return Collection|Livraison[]
     */
    public function getLivraisons(): Collection
    {
        return $this->livraisons;
    }

    public function addLivraison(Livraison $livraison): self
    {
        if (!$this->livraisons->contains($livraison)) {
            $this->livraisons[] = $livraison;
            $livraison->setVeh($this);
        }

        return $this;
    }
    public function __toString() {

        return (string) $this->num_immatriculation;
    }
    public function removeLivraison(Livraison $livraison): self
    {
        if ($this->livraisons->removeElement($livraison)) {
            // set the owning side to null (unless already changed)
            if ($livraison->getVeh() === $this) {
                $livraison->setVeh(null);
            }
        }

        return $this;
    }



}
