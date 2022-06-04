<?php

namespace App\Entity;

use App\Repository\UtilisateurRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Security\Core\User\UserInterface;
use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity;
use Symfony\Component\Serializer\Annotation\Groups;

/**
 * @ORM\Entity(repositoryClass=UtilisateurRepository::class)
 * @UniqueEntity(
 *  fields={"email_u"},
 *     message="l'email que vous avez indiqué est déja utilisé !"
 * )
 */
class Utilisateur implements UserInterface
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     *  *   @Groups("post:read")
     */
    private $id;

    /**
     * @ORM\Column(type="integer")
     * @Assert\Length(min="8",max="8" , minMessage="votre Cin doit contient  8 caracteres")
     * @Assert\NotBlank(message="ce champs est obligatoire")
     *  *   @Groups("post:read")
     */
    private $cin_u;

    /**
     * @ORM\Column(type="string", length=100)
     * @Assert\NotBlank(message="ce champs est obligatoire")
     * @Assert\Valid
     *  *   @Groups("post:read")
     */
    private $nom_u;

    /**
     * @ORM\Column(type="string", length=100)
     * @Assert\NotBlank(message="ce champs est obligatoire")
     *  *   @Groups("post:read")
     */
    private $prenom_u;

    /**
     * @ORM\Column(type="date")
     */
    private $date_naissance;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\Email()
     * @Assert\NotBlank(message="ce champs est obligatoire")
     *  *   @Groups("post:read")
     */
    private $email_u;

    /**
     * @ORM\Column(type="integer")
     *  @Assert\Length(min="8" , minMessage="votre numero doit contient minimum 8 chiffres")
     * @Assert\NotNull(message="ce champs est obligatoire")
     *  *   @Groups("post:read")
     */
    private $num_tel;

    /**
     * @ORM\Column(type="string", length=100)
     * @Assert\NotBlank(message="ce champs est obligatoire")
     *  *   @Groups("post:read")
     */
    private $role;

    /**
     * @ORM\Column(type="string", length=100)
     * @Assert\NotBlank(message="ce champs est obligatoire")
     * @Assert\Length(min="6" , minMessage="votre mot de passe doit contient minimum 6 caracteres")
     *  *   @Groups("post:read")
     */
    private $mot_de_passe;

    /**
     * @Assert\EqualTo(propertyPath="mot_de_passe", message="vous n'aver pas taper le meme mot de passe")
     */
    public $confirme_mot_de_passe;



    /**
     * @ORM\Column(type="string", length=50, nullable=true)
     */
    private $activation_token;

    /**
     * @ORM\Column(type="string", length=50, nullable=true)
     */
    private $reset_token;

    /**
     * @ORM\Column(type="string", length=50, nullable=true)
     */
    private $bloquer_token;


    public function __construct()
    {
        $this->reclamations = new ArrayCollection();
        $this->publications = new ArrayCollection();
        $this->commentaires = new ArrayCollection();
        $this->comptes = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getCinU(): ?int
    {
        return $this->cin_u;
    }

    public function setCinU(?int $cin_u): self
    {
        $this->cin_u = $cin_u;

        return $this;
    }

    public function getNomU(): ?string
    {
        return $this->nom_u;
    }

    public function setNomU(?string $nom_u): self
    {
        $this->nom_u = $nom_u;

        return $this;
    }

    public function getPrenomU(): ?string
    {
        return $this->prenom_u;
    }

    public function setPrenomU(?string $prenom_u): self
    {
        $this->prenom_u = $prenom_u;

        return $this;
    }

    public function getDateNaissance(): ?\DateTimeInterface
    {
        return $this->date_naissance;
    }

    public function setDateNaissance(?\DateTimeInterface $date_naissance): self
    {
        $this->date_naissance = $date_naissance;

        return $this;
    }

    public function getEmailU(): ?string
    {
        return $this->email_u;
    }

    public function setEmailU(?string $email_u): self
    {
        $this->email_u = $email_u;

        return $this;
    }

    public function getNumTel(): ?int
    {
        return $this->num_tel;
    }

    public function setNumTel(?int $num_tel): self
    {
        $this->num_tel = $num_tel;

        return $this;
    }

    public function getRole(): ?string
    {
        return $this->role;
    }

    public function setRole(?string $role): self
    {
        $this->role = $role;

        return $this;
    }

    public function getMotDePasse(): ?string
    {
        return $this->mot_de_passe;
    }

    public function setMotDePasse(?string $mot_de_passe): self
    {
        $this->mot_de_passe = $mot_de_passe;

        return $this;
    }

    public function __toString() {
        return $this->nom_u;
    }

    public function eraseCredentials(){}
    public function getSalt(){}
    public function getRoles(): array{
        $roles[] = $this->role;
        // guarantee every user at least has ROLE_USER
        $roles[] = 'ROLE_USER';
        return array_unique($roles);
    }
    public function getUsername():?string{
        return $this->email_u;
    }
    public function getPassword():?string{
        return $this->mot_de_passe;
    }
    public function getIDd():?int{
        return $this->id;
    }
    public function getActivationToken(): ?string
    {
        return $this->activation_token;
    }

    public function setActivationToken(?string $activation_token): self
    {
        $this->activation_token = $activation_token;

        return $this;
    }

    public function getResetToken(): ?string
    {
        return $this->reset_token;
    }

    public function setResetToken(?string $reset_token): self
    {
        $this->reset_token = $reset_token;

        return $this;
    }

    public function getBloquerToken(): ?string
    {
        return $this->bloquer_token;
    }

    public function setBloquerToken(?string $bloquer_token): self
    {
        $this->bloquer_token = $bloquer_token;

        return $this;
    }


}
