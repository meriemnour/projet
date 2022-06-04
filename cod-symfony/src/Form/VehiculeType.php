<?php

namespace App\Form;

use App\Entity\Livraison;
use App\Entity\Vehicule;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class VehiculeType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('type_vehicule')
            ->add('num_chassis')
            ->add('num_immatriculation')
            /*->add('livraisons', EntityType::class, [
                // looks for choices from this entity
                'class' => Livraison::class,

                'choice_label' => 'nom_livraison',
                'attr'=>[
                    'class'=>"form-control"
                ]

            ] )*/
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Vehicule::class,
        ]);
    }
}
