-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3307
-- Generation Time: Mar 10, 2022 at 02:28 AM
-- Server version: 10.4.20-MariaDB
-- PHP Version: 7.4.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `foody`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `order_list` ()  NO SQL
SELECT o.order_id ,o.customer_id,m.menu_name,p.payment_type,c.adresse AS Address,o.quantity as Qnt FROM orders o INNER JOIN menu m ON m.menu_id=o.menu_id INNER JOIN payment p ON p.order_id=o.order_id INNER JOIN user c ON c.id=o.customer_id WHERE o.order_status='PAYMENT_CONFIRMED' ORDER BY p.time_stamp ASC$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `bonplan`
--

CREATE TABLE `bonplan` (
  `id_bonplan` int(11) NOT NULL,
  `ref_compte` int(11) NOT NULL,
  `refcategorie` int(11) NOT NULL,
  `idrestaurant` int(11) NOT NULL,
  `status` tinyint(1) NOT NULL,
  `libelle` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `image` varchar(255) NOT NULL,
  `prix` double NOT NULL,
  `ouverture` date NOT NULL,
  `fermuture` date NOT NULL,
  `nbrreact` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `bonplan`
--

INSERT INTO `bonplan` (`id_bonplan`, `ref_compte`, `refcategorie`, `idrestaurant`, `status`, `libelle`, `description`, `image`, `prix`, `ouverture`, `fermuture`, `nbrreact`) VALUES
(1, 2, 3, 1, 1, 'dfg', 'dfg', 'stay1.jpg', 22, '2022-03-09', '2022-03-09', 2),
(5, 25, 2, 1, 1, 'testaaa', 'testaaa', 'stay1.jpg', 22, '2022-03-09', '2022-03-09', 2);

-- --------------------------------------------------------

--
-- Table structure for table `livraison`
--

CREATE TABLE `livraison` (
  `id_livraison` int(11) NOT NULL,
  `nom_livraison` varchar(255) DEFAULT NULL,
  `date` varchar(255) DEFAULT NULL,
  `destination` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `livraison`
--

INSERT INTO `livraison` (`id_livraison`, `nom_livraison`, `date`, `destination`) VALUES
(9, 'salim', '2022-03-24', 'manar'),
(2, 'majdi', '2022-02-16', 'mourouj'),
(10, 'hamadi', '2022-02-24', 'khelidia'),
(4, 'majdouch', '2022-02-19', 'mouroujjjjjj'),
(5, 'majdi', '2022-02-17', 'mourouj'),
(6, 'djouch', '2022-02-17', 'ariana'),
(21, 'hamadino', '2022-02-24', 'khelidia'),
(11, 'hamadi', '2022-02-24', 'khelidia'),
(12, 'majdouch', '2022-24-02', 'kelibia'),
(22, 'majdonit', '2022-02-16', 'mourouj'),
(23, 'karim', '1967-11-13', 'khelidia'),
(24, 'cameroon', '2022-03-10', 'cameroon');

-- --------------------------------------------------------

--
-- Table structure for table `menu`
--

CREATE TABLE `menu` (
  `menu_id` int(255) NOT NULL,
  `menu_name` varchar(255) NOT NULL,
  `price` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `menu`
--

INSERT INTO `menu` (`menu_id`, `menu_name`, `price`) VALUES
(1, 'American Pizza', 35),
(2, 'Veg Pizza', 25),
(3, 'Chicken Pizza', 40),
(4, 'Pepperroni Pizza', 45),
(5, 'Veg Burger', 50),
(6, 'Chicken Burger', 80),
(7, 'Power Burger', 30),
(8, 'Sandwich Burger', 18),
(9, 'Fatma Fingers', 20),
(10, 'Donut', 25),
(11, 'Noodles', 30),
(12, 'Waffle', 15),
(25, 'makarouna', 11),
(26, 'loubia', 12);

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `order_id` int(255) NOT NULL,
  `customer_id` int(255) NOT NULL,
  `menu_id` int(255) NOT NULL,
  `quantity` int(255) NOT NULL DEFAULT 1,
  `order_status` enum('ADDED_TO_CART','CONFIRMED','PAYMENT_CONFIRMED','DELIVERED') DEFAULT NULL,
  `time_stamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`order_id`, `customer_id`, `menu_id`, `quantity`, `order_status`, `time_stamp`) VALUES
(210, 32, 1, 1, 'PAYMENT_CONFIRMED', '2022-03-08 13:56:18'),
(211, 32, 1, 1, 'PAYMENT_CONFIRMED', '2022-03-08 13:59:26'),
(212, 32, 3, 1, 'PAYMENT_CONFIRMED', '2022-03-08 13:59:26'),
(213, 32, 4, 1, 'PAYMENT_CONFIRMED', '2022-03-08 13:59:26'),
(214, 32, 2, 2, 'DELIVERED', '2022-03-08 16:33:15'),
(216, 32, 1, 1, 'PAYMENT_CONFIRMED', '2022-03-08 15:54:54'),
(217, 32, 2, 1, 'DELIVERED', '2022-03-08 16:33:20'),
(218, 32, 6, 1, 'PAYMENT_CONFIRMED', '2022-03-08 15:54:54'),
(219, 32, 1, 1, 'ADDED_TO_CART', '2022-03-09 15:20:43'),
(220, 34, 3, 1, 'PAYMENT_CONFIRMED', '2022-03-09 15:57:07'),
(221, 34, 9, 1, 'PAYMENT_CONFIRMED', '2022-03-09 15:57:07'),
(222, 34, 2, 1, 'PAYMENT_CONFIRMED', '2022-03-09 15:57:07'),
(223, 34, 1, 2, 'PAYMENT_CONFIRMED', '2022-03-09 15:57:07'),
(227, 34, 6, 1, 'ADDED_TO_CART', '2022-03-09 16:16:19');

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE `payment` (
  `id` int(255) NOT NULL,
  `order_id` int(255) NOT NULL,
  `payment_type` enum('CASH_ON_DELIVERY','ONLINE_PAYMENT') NOT NULL DEFAULT 'CASH_ON_DELIVERY',
  `payment_status` enum('NOT_CONFIRMED','CONFIRMED') NOT NULL DEFAULT 'NOT_CONFIRMED',
  `time_stamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `payment`
--

INSERT INTO `payment` (`id`, `order_id`, `payment_type`, `payment_status`, `time_stamp`) VALUES
(271, 211, 'CASH_ON_DELIVERY', 'CONFIRMED', '2022-03-08 13:59:26'),
(272, 212, 'CASH_ON_DELIVERY', 'CONFIRMED', '2022-03-08 13:59:26'),
(273, 213, 'CASH_ON_DELIVERY', 'CONFIRMED', '2022-03-08 13:59:26'),
(274, 214, 'CASH_ON_DELIVERY', 'CONFIRMED', '2022-03-08 13:59:26'),
(278, 216, 'ONLINE_PAYMENT', 'CONFIRMED', '2022-03-08 15:54:54'),
(279, 217, 'ONLINE_PAYMENT', 'CONFIRMED', '2022-03-08 15:54:54'),
(280, 218, 'ONLINE_PAYMENT', 'CONFIRMED', '2022-03-08 15:54:54'),
(281, 220, 'CASH_ON_DELIVERY', 'CONFIRMED', '2022-03-09 15:57:07'),
(282, 221, 'CASH_ON_DELIVERY', 'CONFIRMED', '2022-03-09 15:57:07'),
(283, 222, 'CASH_ON_DELIVERY', 'CONFIRMED', '2022-03-09 15:57:07'),
(284, 223, 'CASH_ON_DELIVERY', 'CONFIRMED', '2022-03-09 15:57:07');

-- --------------------------------------------------------

--
-- Table structure for table `payment_details`
--

CREATE TABLE `payment_details` (
  `payment_id` int(255) NOT NULL,
  `customer_id` int(255) NOT NULL,
  `card_number` varchar(16) NOT NULL,
  `card_holder_name` varchar(255) NOT NULL,
  `cvv` int(3) NOT NULL,
  `exp_month` int(2) NOT NULL,
  `exp_year` int(4) NOT NULL,
  `time_stamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `payment_details`
--

INSERT INTO `payment_details` (`payment_id`, `customer_id`, `card_number`, `card_holder_name`, `cvv`, `exp_month`, `exp_year`, `time_stamp`) VALUES
(19, 32, '1234567891234567', 'ilyes', 123, 12, 25, '2022-03-08 15:54:54');

-- --------------------------------------------------------

--
-- Table structure for table `rating`
--

CREATE TABLE `rating` (
  `rating_id` int(11) NOT NULL,
  `rating_num` double NOT NULL,
  `menu_id` int(11) NOT NULL,
  `compteur` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `rating`
--

INSERT INTO `rating` (`rating_id`, `rating_num`, `menu_id`, `compteur`) VALUES
(1, 3.5, 1, 6),
(2, 8, 2, 7),
(3, 3.5, 3, 2),
(4, 3.5, 4, 2),
(5, 3.4, 5, 5),
(6, 4, 6, 3),
(7, 5, 7, 2),
(8, 3.5, 8, 2),
(9, 4.5, 9, 2),
(10, 3.2, 10, 10),
(11, 3, 11, 2),
(12, 3, 12, 2);

-- --------------------------------------------------------

--
-- Table structure for table `reclamation`
--

CREATE TABLE `reclamation` (
  `id_reclamation` int(11) NOT NULL,
  `id_author` int(11) NOT NULL,
  `id_bonplan` int(11) NOT NULL,
  `date_creation` date NOT NULL,
  `date_traitement` date DEFAULT NULL,
  `description` varchar(255) NOT NULL,
  `objet` varchar(255) NOT NULL,
  `status` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `reclamation`
--

INSERT INTO `reclamation` (`id_reclamation`, `id_author`, `id_bonplan`, `date_creation`, `date_traitement`, `description`, `objet`, `status`) VALUES
(1, 1, 5, '2022-03-09', NULL, 'eee', 'aaa', 1),
(2, 1, 5, '2022-03-09', '2022-03-09', 'aaaa', 'aa', 1),
(3, 1, 1, '2022-03-09', NULL, 'aaaa', 'aa', 1);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `adresse` varchar(255) DEFAULT NULL,
  `date_naissance` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `num_tel` bigint(20) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `adresse`, `date_naissance`, `email`, `nom`, `num_tel`, `password`, `prenom`, `role`, `username`, `status`) VALUES
(8, 'ariana', '2022-02-01', 'meriem@esprit.tn', 'meriem', 52778549, '1234', 'ghozzi', 'CLIENT', 'client', 1),
(10, 'rue des camelias', '2022-02-27', 'mohamed@gmail.com', 'meriem', 50737864, 'admin', 'meriem', 'ADMIN', 'admin', 1),
(14, 'lac', '2022-03-14', 'meriemnour.ghozzi@esprit.tn', 'meriem nour', 52778549, '123', 'ghozzi', 'CLIENT', 'mimou', 0),
(15, 'benzart', '2022-04-05', 'meriemnour@esprit.tn', 'mimou', 52778549, 'mimou', 'meriem', 'COLLABORATEUR', 'mimou123', 1),
(19, 'lac', '2022-03-23', 'majdi.bardi@esprit.tn', 'mejdi', 52816356, '147', 'bardi', 'LIVREUR', 'mejdi', 0),
(20, 'lac', '2022-03-17', 'meriemnour.ghozzi@esprit.tn', 'meriem', 52778549, '123', 'nour', 'CLIENT', 'mimou7', 0),
(26, 'benzarte', '2022-03-22', 'meriem@gmail.com', 'merie', 52778549, 'mimou', 'marie', 'CLIENT', 'marie', 0),
(32, 'aaaadachra', '1999-03-18', 'issam@gmail.com', 'lala', 78945612, '123456', 'ilyes', 'RESPONSABLE', 'ilyesss', 1),
(33, 'lac', '2015-03-05', 'meriem@gmail.com', 'meriem', 52778549, '123', 'nour', 'CLIENT', 'mimou00', 0),
(34, 'sfaxbirali', '1999-03-18', 'issamchaaben@gmail.com', 'chaaben', 12345678, '123456', 'issam', 'LIVREUR', 'majdi', 1),
(35, 'test', '2022-03-07', 'test@gmail.com', 'test', 11444777, 'test', 'test', 'CLIENT', 'test', 1);

-- --------------------------------------------------------

--
-- Table structure for table `user_archive`
--

CREATE TABLE `user_archive` (
  `id` int(11) NOT NULL,
  `adresse` varchar(255) DEFAULT NULL,
  `date_naissance` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `num_tel` int(11) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user_archive`
--

INSERT INTO `user_archive` (`id`, `adresse`, `date_naissance`, `email`, `nom`, `num_tel`, `password`, `prenom`, `role`, `username`, `status`) VALUES
(1, 'zahra', '2022-06-28', 'ahmed@esprit.tn', 'nafti', 92702533, '123456789', 'ahmed amine', 'CLIENT', 'ahmed', '1'),
(2, 'rue des camelias', '2000-02-20', 'mohamed@gmail.com', 'hosni', 50737864, 'collaborateur', 'hosni', 'CLIENT', 'collaborateur', '1'),
(3, 'rue des camelias', '2018-02-12', 'mohamed@gmail.com', 'mohamed', 50737864, 'admin', 'slaimi', 'ADMIN', 'admin', '1');

-- --------------------------------------------------------

--
-- Table structure for table `véhicule`
--

CREATE TABLE `véhicule` (
  `id_vehicule` int(11) NOT NULL,
  `type_vehicule` varchar(255) DEFAULT NULL,
  `num_chassis` varchar(255) DEFAULT NULL,
  `num_immatriculation` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `véhicule`
--

INSERT INTO `véhicule` (`id_vehicule`, `type_vehicule`, `num_chassis`, `num_immatriculation`) VALUES
(3, 'clio', 'vf541kh', '112tn4101'),
(7, 'bmw', 'vf541kh', '116'),
(5, 'audi', 'vf541kh', '116'),
(8, 'dacia', 'allgood', '211');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bonplan`
--
ALTER TABLE `bonplan`
  ADD PRIMARY KEY (`id_bonplan`);

--
-- Indexes for table `livraison`
--
ALTER TABLE `livraison`
  ADD PRIMARY KEY (`id_livraison`);

--
-- Indexes for table `menu`
--
ALTER TABLE `menu`
  ADD PRIMARY KEY (`menu_id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`order_id`),
  ADD KEY `customer_id` (`customer_id`),
  ADD KEY `menu_id` (`menu_id`);

--
-- Indexes for table `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `order_id` (`order_id`);

--
-- Indexes for table `payment_details`
--
ALTER TABLE `payment_details`
  ADD PRIMARY KEY (`payment_id`),
  ADD KEY `customer_id` (`customer_id`);

--
-- Indexes for table `rating`
--
ALTER TABLE `rating`
  ADD PRIMARY KEY (`rating_id`),
  ADD UNIQUE KEY `menu_id` (`menu_id`);

--
-- Indexes for table `reclamation`
--
ALTER TABLE `reclamation`
  ADD PRIMARY KEY (`id_reclamation`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_archive`
--
ALTER TABLE `user_archive`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `véhicule`
--
ALTER TABLE `véhicule`
  ADD PRIMARY KEY (`id_vehicule`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bonplan`
--
ALTER TABLE `bonplan`
  MODIFY `id_bonplan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `livraison`
--
ALTER TABLE `livraison`
  MODIFY `id_livraison` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `menu`
--
ALTER TABLE `menu`
  MODIFY `menu_id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `order_id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=228;

--
-- AUTO_INCREMENT for table `payment`
--
ALTER TABLE `payment`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=288;

--
-- AUTO_INCREMENT for table `payment_details`
--
ALTER TABLE `payment_details`
  MODIFY `payment_id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `reclamation`
--
ALTER TABLE `reclamation`
  MODIFY `id_reclamation` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- AUTO_INCREMENT for table `user_archive`
--
ALTER TABLE `user_archive`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `véhicule`
--
ALTER TABLE `véhicule`
  MODIFY `id_vehicule` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`menu_id`);

--
-- Constraints for table `payment`
--
ALTER TABLE `payment`
  ADD CONSTRAINT `payment_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`);

--
-- Constraints for table `payment_details`
--
ALTER TABLE `payment_details`
  ADD CONSTRAINT `payment_details_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `user` (`id`);

--
-- Constraints for table `rating`
--
ALTER TABLE `rating`
  ADD CONSTRAINT `rating_ibfk_1` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`menu_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
