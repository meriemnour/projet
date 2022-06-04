<?php

namespace App\Repository;

use App\Entity\Chequier;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @method Chequier|null find($id, $lockMode = null, $lockVersion = null)
 * @method Chequier|null findOneBy(array $criteria, array $orderBy = null)
 * @method Chequier[]    findAll()
 * @method Chequier[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class ChequierRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Chequier::class);
    }

    // /**
    //  * @return Chequier[] Returns an array of Chequier objects
    //  */
    /*
    public function findByExampleField($value)
    {
        return $this->createQueryBuilder('c')
            ->andWhere('c.exampleField = :val')
            ->setParameter('val', $value)
            ->orderBy('c.id', 'ASC')
            ->setMaxResults(10)
            ->getQuery()
            ->getResult()
        ;
    }
    */

    /*
    public function findOneBySomeField($value): ?Chequier
    {
        return $this->createQueryBuilder('c')
            ->andWhere('c.exampleField = :val')
            ->setParameter('val', $value)
            ->getQuery()
            ->getOneOrNullResult()
        ;
    }
    */
}
