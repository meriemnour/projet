<?php

namespace App\Entity;

use App\Repository\PublicationRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity(repositoryClass=PublicationRepository::class)
 */
class Publication
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
    private $id_publication;

    /**
     * @ORM\ManyToOne(targetEntity=Utilisateur::class, inversedBy="publications")
     */
    private $nom_client;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $Titre_publication;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $Description_publication;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $categorie_publication;

    /**
     * @ORM\Column(type="datetime")
     */
    private $date_publication;

    /**
     * @ORM\OneToMany(targetEntity=Commentaire::class, mappedBy="id_publication")
     */
    private $commentaires;

    public function __construct()
    {
        $this->commentaires = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getIdPublication(): ?int
    {
        return $this->id_publication;
    }

    public function setIdPublication(int $id_publication): self
    {
        $this->id_publication = $id_publication;

        return $this;
    }

    public function getNomClient(): ?Utilisateur
    {
        return $this->nom_client;
    }

    public function setNomClient(?Utilisateur $nom_client): self
    {
        $this->nom_client = $nom_client;

        return $this;
    }

    public function getTitrePublication(): ?string
    {
        return $this->Titre_publication;
    }

    public function setTitrePublication(string $Titre_publication): self
    {
        $this->Titre_publication = $Titre_publication;

        return $this;
    }

    public function getDescriptionPublication(): ?string
    {
        return $this->Description_publication;
    }

    public function setDescriptionPublication(string $Description_publication): self
    {
        $this->Description_publication = $Description_publication;

        return $this;
    }

    public function getCategoriePublication(): ?string
    {
        return $this->categorie_publication;
    }

    public function setCategoriePublication(string $categorie_publication): self
    {
        $this->categorie_publication = $categorie_publication;

        return $this;
    }

    public function getDatePublication(): ?\DateTimeInterface
    {
        return $this->date_publication;
    }

    public function setDatePublication(\DateTimeInterface $date_publication): self
    {
        $this->date_publication = $date_publication;

        return $this;
    }

    /**
     * @return Collection|Commentaire[]
     */
    public function getCommentaires(): Collection
    {
        return $this->commentaires;
    }

    public function addCommentaire(Commentaire $commentaire): self
    {
        if (!$this->commentaires->contains($commentaire)) {
            $this->commentaires[] = $commentaire;
            $commentaire->setIdPublication($this);
        }

        return $this;
    }

    public function removeCommentaire(Commentaire $commentaire): self
    {
        if ($this->commentaires->removeElement($commentaire)) {
            // set the owning side to null (unless already changed)
            if ($commentaire->getIdPublication() === $this) {
                $commentaire->setIdPublication(null);
            }
        }

        return $this;
    }
}
