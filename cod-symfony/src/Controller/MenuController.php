<?php

namespace App\Controller;

use App\Entity\Menu;
use App\Form\MenuType;
use App\Form\SearchType;
use App\Repository\MenuRepository;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Dompdf\Dompdf;
use Dompdf\Options;

class MenuController extends AbstractController
{
    /**
     * @Route("/menu", name="display_menu")
     */
    public function index(PaginatorInterface $paginator): Response
    {
        $this->denyAccessUnlessGranted('ROLE_EMPLOYEE','ROLE_ADMIN');
        $menus = $this->getDoctrine()->getManager()->getRepository(Menu::class)->findAll();
        return $this->render('menu/indexx.html.twig', ['m' => $menus]

        );
    }
    /**
     * @Route("/menulist",name="menulist")
     */
    public function list(MenuRepository  $M, Request $request ,PaginatorInterface $paginator )
    {
        $list= $paginator->paginate($M->findAll(), $request->query->getInt('page', 1),4) ;
        $formSearch=$this->createForm(SearchType::class);
        $formSearch->handleRequest($request);
        if($formSearch->isSubmitted() ){
            $name = $formSearch->getData();
            $TSearch = $paginator->paginate($M->searchCathegorie($name), $request->query->getInt('page', 1),4);
            return $this->render("menu/indexsearch.html.twig", array('m'=>$list , "cath"=>$TSearch , "formSearch"=>$formSearch->createView()));
        }
        return $this->render("menu/index.html.twig", array('m'=>$list, "formSearch"=>$formSearch->createView()));
    }



    /**
     * @Route("/AddMenu", name="Add_Menu")
     */
    public function AddMenu(Request $request): Response
    {
        $menu = new Menu();
        $form = $this->createForm(MenuType::class, $menu);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($menu);
            $em->flush();
            $this->addFlash('info','added successfully!');
            return $this->redirectToRoute('display_menu');

        }
        return $this->render('menu/createmenu.html.twig', [
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/RemoveMenu/{id}", name="Remove_Menu")
     */
    public function RemoveMenu(Menu $menu): Response
    {
        {
            $em = $this->getDoctrine()->getManager();
            $em->remove($menu);
            $em->flush();
            $this->addFlash('info2','Menu Deleted!');

            return $this->redirectToRoute('display_menu');
        }
    }
    /**
     * @Route("/UpdateMenu/{id}", name="Update_Menu")
     */
    public function UpdateMenu(Request $request , $id): Response
    {$menu = $this->getDoctrine()->getManager()->getRepository(Menu::class)->find($id);
        $form = $this->createForm(MenuType::class, $menu);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->flush();
            $this->addFlash('info3','Menu Updated!');

            return $this->redirectToRoute('display_menu');

        }
        return $this->render('menu/updatemenu.html.twig', [
            'form' => $form->createView(),
        ]);
    }


    /**
     * @Route("/stats", name="stats")
     */
    public function statistiques(MenuRepository $menuRepo){
        // On va chercher toutes les menus
        $menus = $menuRepo->findAll();

//Data Category
        $Starters = $menuRepo->createQueryBuilder('a')
            ->select('count(a.id)')
            ->Where('a.category= :category')
            ->setParameter('category',"Starters")
            ->getQuery()
            ->getSingleScalarResult();

        $fastf = $menuRepo->createQueryBuilder('a')
            ->select('count(a.id)')
            ->Where('a.category= :category')
            ->setParameter('category',"Fast Foods")
            ->getQuery()
            ->getSingleScalarResult();
        $Vegetarian= $menuRepo->createQueryBuilder('a')
            ->select('count(a.id)')
            ->Where('a.category= :category')
            ->setParameter('category',"Vegetarian")
            ->getQuery()
            ->getSingleScalarResult();

        //data Prix
        $prix1= $menuRepo->createQueryBuilder('b')
            ->select('count(b.id)')
            ->andWhere('b.price < 50')
            ->getQuery()
            ->getSingleScalarResult();
        $prix2= $menuRepo->createQueryBuilder('b')
            ->select('count(b.id)')
            ->andWhere('b.price > 50')
            ->andWhere('b.price < 100')
            ->getQuery()
            ->getSingleScalarResult();
        $prix3= $menuRepo->createQueryBuilder('b')
            ->select('count(b.id)')
            ->andWhere('b.price > 100')
            ->andWhere('b.price < 150')
            ->getQuery()
            ->getSingleScalarResult();
        $prix4= $menuRepo->createQueryBuilder('b')
            ->select('count(b.id)')
            ->andWhere('b.price > 150')
            ->getQuery()
            ->getSingleScalarResult();

        return $this->render('Stats/stats.html.twig', [
            'nStarters' => $Starters,
            'nVegetarian' => $Vegetarian,
            'nff' => $fastf,
            'prix1' => $prix1,
            'prix2' => $prix2,
            'prix3' => $prix3,
            'prix4' => $prix4,

        ]);
    }
    /**
     * @Route("/Trierprix", name="trie1",methods={"GET"})
     */

    public function triPrix(Request $request, MenuRepository $menuRepository): Response
    {

        $menuRepository = $this->getDoctrine()->getRepository(Menu::class);
        $menu= $menuRepository->trierilyes();

        return $this->render('menu/indexx.html.twig', [
            'm' => $menu,
        ]);
    }
    /**
     * @Route("/Trierid", name="trie2",methods={"GET"})
     */

    public function triId(Request $request, MenuRepository $menuRepository): Response
    {

        $menuRepository = $this->getDoctrine()->getRepository(Menu::class);
        $menu= $menuRepository->trierilyes1();

        return $this->render('menu/indexx.html.twig', [
            'm' => $menu,
        ]);
    }
    /**
     * @Route("/Triercategory", name="trie3",methods={"GET"})
     */

    public function triCategory(Request $request, MenuRepository $menuRepository): Response
    {

        $menuRepository = $this->getDoctrine()->getRepository(Menu::class);
        $menu= $menuRepository->trierilyes2();

        return $this->render('menu/indexx.html.twig', [
            'm' => $menu,
        ]);
    }
    /**
     * @Route("/showpdf",name="showpdf")
     */
    public function showpdf()
    {

        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');
        $pdfOptions->setIsRemoteEnabled(true);
        $pdfOptions->setIsFontSubsettingEnabled(true) ;

        // Instantiate Dompdf with our options
        $dompdf = new Dompdf($pdfOptions);
        $M= $this->getDoctrine()->
        getRepository(Menu::class)->findAll();
        // Retrieve the HTML generated in our twig file
        $html = $this->renderView('menu/pdf.html.twig',
            ['m'=>$M,]);

        // Load HTML to Dompdf
        $dompdf->loadHtml($html);
        // (Optional) Setup the paper size and orientation 'portrait' or 'portrait'
        $dompdf->setPaper('A4', 'portrait');

        // Render the HTML as PDF
        $dompdf->render();

        // Output the generated PDF to Browser (force download)
        $dompdf->stream("mypdf.pdf", [
            "Attachment" => true
        ]);

    }
}