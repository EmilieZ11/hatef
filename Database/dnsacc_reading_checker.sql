-- phpMyAdmin SQL Dump
-- version 4.9.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Nov 24, 2020 at 12:48 PM
-- Server version: 5.7.28
-- PHP Version: 7.3.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dnsacc_reading_checker`
--

-- --------------------------------------------------------

--
-- Table structure for table `admins`
--

CREATE TABLE `admins` (
  `id` int(6) UNSIGNED NOT NULL,
  `user` varchar(20) NOT NULL,
  `pass` varchar(20) NOT NULL,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_persian_ci DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admins`
--

INSERT INTO `admins` (`id`, `user`, `pass`, `name`) VALUES
(1, 'yazahra77', '1869', 'محمد شریفیان');

-- --------------------------------------------------------

--
-- Table structure for table `mssg_s`
--

CREATE TABLE `mssg_s` (
  `id` int(20) UNSIGNED NOT NULL,
  `text` varchar(2530) CHARACTER SET utf8 COLLATE utf8_persian_ci NOT NULL,
  `admn` tinyint(1) NOT NULL,
  `time` varchar(100) NOT NULL,
  `seen` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(6) UNSIGNED NOT NULL,
  `user` varchar(20) NOT NULL,
  `pass` varchar(20) CHARACTER SET utf8 COLLATE utf8_persian_ci NOT NULL,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_persian_ci DEFAULT NULL,
  `regd` varchar(100) DEFAULT NULL,
  `admn` varchar(20) NOT NULL,
  `verf` tinyint(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `user`, `pass`, `name`, `regd`, `admn`, `verf`) VALUES
(38, 'zahra', 'ss', 'حسین ستاره ای', '1558346370', 'yazahra77', 1),
(30, 'z', '1398', 'هومن امیری', '1556302921', 'yazahra77', 1),
(33, 'massomeh', '99)', 'حسین ستاره ای ', '1556989750', 'yazahra77', 1),
(40, 'zohra', 'ss(', 'حسین ستاره ای ', '1559334514', 'yazahra77', 1),
(32, 'a', '40', 'علیرضاساربان', '1556911746', 'yazahra77', 1),
(37, 'ali', '1414', 'علی صیادی پور', '1558323901', 'yazahra77', 1),
(36, 'm', '1414', '  علی صیادی پور', '1557403590', 'yazahra77', 1),
(24, 'sa', 's', 'محمد یاسین سعیدیان', '1556199306', 'yazahra77', 1),
(35, '15666666666', '80996240', '15666666666', '1557285285', 'yazahra77', 1),
(26, 'ha', 'h', 'حامد دارآفرین', '1556199508', 'yazahra77', 1),
(27, 's', 'd', 'عرفان', '1556209567', 'yazahra77', 1),
(45, 'f', '1234', 'مهدی صفری', '1559839905', 'yazahra77', 1),
(47, 'v', '1234', 'مهدی صفری', '1559840211', 'yazahra77', 0),
(46, 'k', '1234', 'مهدی صفری', '1559839925', 'yazahra77', 0);

-- --------------------------------------------------------

--
-- Table structure for table `user_a`
--

CREATE TABLE `user_a` (
  `id` int(6) UNSIGNED NOT NULL,
  `date` varchar(20) NOT NULL,
  `item_1` int(1) NOT NULL,
  `item_2` int(1) NOT NULL,
  `item_3` int(1) NOT NULL,
  `item_4` int(1) NOT NULL,
  `item_5` int(1) NOT NULL,
  `item_6` int(1) NOT NULL,
  `item_7` int(1) NOT NULL,
  `item_8` int(1) NOT NULL,
  `item_9` int(1) NOT NULL,
  `item_10` int(1) NOT NULL,
  `item_11` int(1) NOT NULL,
  `item_12` int(1) NOT NULL,
  `item_13` int(1) NOT NULL,
  `item_14` int(1) NOT NULL,
  `mess` varchar(5220) CHARACTER SET utf8 COLLATE utf8_persian_ci DEFAULT NULL,
  `seen` tinyint(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_a`
--

INSERT INTO `user_a` (`id`, `date`, `item_1`, `item_2`, `item_3`, `item_4`, `item_5`, `item_6`, `item_7`, `item_8`, `item_9`, `item_10`, `item_11`, `item_12`, `item_13`, `item_14`, `mess`, `seen`) VALUES
(1, '1556913403', 3, 1, 3, 0, 3, 2, 1, 2, 2, 1, 3, 3, 1, 2, '', 0);

-- --------------------------------------------------------

--
-- Table structure for table `user_ali`
--

CREATE TABLE `user_ali` (
  `id` int(6) UNSIGNED NOT NULL,
  `date` varchar(20) NOT NULL,
  `item_1` int(1) NOT NULL,
  `item_2` int(1) NOT NULL,
  `item_3` int(1) NOT NULL,
  `item_4` int(1) NOT NULL,
  `item_5` int(1) NOT NULL,
  `item_6` int(1) NOT NULL,
  `item_7` int(1) NOT NULL,
  `item_8` int(1) NOT NULL,
  `item_9` int(1) NOT NULL,
  `item_10` int(1) NOT NULL,
  `item_11` int(1) NOT NULL,
  `item_12` int(1) NOT NULL,
  `item_13` int(1) NOT NULL,
  `item_14` int(1) NOT NULL,
  `mess` varchar(5220) CHARACTER SET utf8 COLLATE utf8_persian_ci DEFAULT NULL,
  `seen` tinyint(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_ali`
--

INSERT INTO `user_ali` (`id`, `date`, `item_1`, `item_2`, `item_3`, `item_4`, `item_5`, `item_6`, `item_7`, `item_8`, `item_9`, `item_10`, `item_11`, `item_12`, `item_13`, `item_14`, `mess`, `seen`) VALUES
(1, '1558324023', 1, 2, 1, 1, 1, 2, 2, 1, 2, 1, 1, 2, 2, 2, '2=---q-v-d-z---7=', 0);

-- --------------------------------------------------------

--
-- Table structure for table `user_ha`
--

CREATE TABLE `user_ha` (
  `id` int(6) UNSIGNED NOT NULL,
  `date` varchar(20) NOT NULL,
  `item_1` int(1) NOT NULL,
  `item_2` int(1) NOT NULL,
  `item_3` int(1) NOT NULL,
  `item_4` int(1) NOT NULL,
  `item_5` int(1) NOT NULL,
  `item_6` int(1) NOT NULL,
  `item_7` int(1) NOT NULL,
  `item_8` int(1) NOT NULL,
  `item_9` int(1) NOT NULL,
  `item_10` int(1) NOT NULL,
  `item_11` int(1) NOT NULL,
  `item_12` int(1) NOT NULL,
  `item_13` int(1) NOT NULL,
  `item_14` int(1) NOT NULL,
  `mess` varchar(5220) CHARACTER SET utf8 COLLATE utf8_persian_ci DEFAULT NULL,
  `seen` tinyint(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_ha`
--

INSERT INTO `user_ha` (`id`, `date`, `item_1`, `item_2`, `item_3`, `item_4`, `item_5`, `item_6`, `item_7`, `item_8`, `item_9`, `item_10`, `item_11`, `item_12`, `item_13`, `item_14`, `mess`, `seen`) VALUES
(1, '1556199780', 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, '', 0);

-- --------------------------------------------------------

--
-- Table structure for table `user_m`
--

CREATE TABLE `user_m` (
  `id` int(6) UNSIGNED NOT NULL,
  `date` varchar(20) NOT NULL,
  `item_1` int(1) NOT NULL,
  `item_2` int(1) NOT NULL,
  `item_3` int(1) NOT NULL,
  `item_4` int(1) NOT NULL,
  `item_5` int(1) NOT NULL,
  `item_6` int(1) NOT NULL,
  `item_7` int(1) NOT NULL,
  `item_8` int(1) NOT NULL,
  `item_9` int(1) NOT NULL,
  `item_10` int(1) NOT NULL,
  `item_11` int(1) NOT NULL,
  `item_12` int(1) NOT NULL,
  `item_13` int(1) NOT NULL,
  `item_14` int(1) NOT NULL,
  `mess` varchar(5220) CHARACTER SET utf8 COLLATE utf8_persian_ci DEFAULT NULL,
  `seen` tinyint(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_m`
--

INSERT INTO `user_m` (`id`, `date`, `item_1`, `item_2`, `item_3`, `item_4`, `item_5`, `item_6`, `item_7`, `item_8`, `item_9`, `item_10`, `item_11`, `item_12`, `item_13`, `item_14`, `mess`, `seen`) VALUES
(1, '1557403881', 1, 2, 1, 1, 1, 3, 2, 2, 1, 1, 1, 3, 2, 3, '', 0);

-- --------------------------------------------------------

--
-- Table structure for table `user_massomeh`
--

CREATE TABLE `user_massomeh` (
  `id` int(6) UNSIGNED NOT NULL,
  `date` varchar(20) NOT NULL,
  `item_1` int(1) NOT NULL,
  `item_2` int(1) NOT NULL,
  `item_3` int(1) NOT NULL,
  `item_4` int(1) NOT NULL,
  `item_5` int(1) NOT NULL,
  `item_6` int(1) NOT NULL,
  `item_7` int(1) NOT NULL,
  `item_8` int(1) NOT NULL,
  `item_9` int(1) NOT NULL,
  `item_10` int(1) NOT NULL,
  `item_11` int(1) NOT NULL,
  `item_12` int(1) NOT NULL,
  `item_13` int(1) NOT NULL,
  `item_14` int(1) NOT NULL,
  `mess` varchar(5220) CHARACTER SET utf8 COLLATE utf8_persian_ci DEFAULT NULL,
  `seen` tinyint(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_massomeh`
--

INSERT INTO `user_massomeh` (`id`, `date`, `item_1`, `item_2`, `item_3`, `item_4`, `item_5`, `item_6`, `item_7`, `item_8`, `item_9`, `item_10`, `item_11`, `item_12`, `item_13`, `item_14`, `mess`, `seen`) VALUES
(1, '1556990242', 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, '', 1);

-- --------------------------------------------------------

--
-- Table structure for table `user_s`
--

CREATE TABLE `user_s` (
  `id` int(6) UNSIGNED NOT NULL,
  `date` varchar(20) NOT NULL,
  `item_1` int(1) NOT NULL,
  `item_2` int(1) NOT NULL,
  `item_3` int(1) NOT NULL,
  `item_4` int(1) NOT NULL,
  `item_5` int(1) NOT NULL,
  `item_6` int(1) NOT NULL,
  `item_7` int(1) NOT NULL,
  `item_8` int(1) NOT NULL,
  `item_9` int(1) NOT NULL,
  `item_10` int(1) NOT NULL,
  `item_11` int(1) NOT NULL,
  `item_12` int(1) NOT NULL,
  `item_13` int(1) NOT NULL,
  `item_14` int(1) NOT NULL,
  `mess` varchar(5220) CHARACTER SET utf8 COLLATE utf8_persian_ci DEFAULT NULL,
  `seen` tinyint(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_s`
--

INSERT INTO `user_s` (`id`, `date`, `item_1`, `item_2`, `item_3`, `item_4`, `item_5`, `item_6`, `item_7`, `item_8`, `item_9`, `item_10`, `item_11`, `item_12`, `item_13`, `item_14`, `mess`, `seen`) VALUES
(1, '1556982879', 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, '', 1),
(2, '1557492932', 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, '', 0),
(3, '1558737391', 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, '', 0),
(4, '1559904655', 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, '0=نماز میخواند ولی بعضی وقتها نمازش قضا میشود ---q-v-d-z---5=تعطیل---q-v-d-z---7=تعطیل ---q-v-d-z---8=تکلیف ندارد ---q-v-d-z---13=دیر میخوابد', 0),
(5, '1560604525', 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, '', 1),
(6, '1561837765', 2, 2, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 2, '', 0),
(7, '1565599217', 2, 2, 1, 1, 2, 0, 0, 0, 0, 1, 1, 1, 2, 2, '', 0),
(8, '1575124866', 2, 2, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 2, '', 0),
(9, '1582038672', 2, 1, 1, 1, 3, 1, 1, 1, 1, 1, 2, 1, 2, 2, '', 0);

-- --------------------------------------------------------

--
-- Table structure for table `user_sa`
--

CREATE TABLE `user_sa` (
  `id` int(6) UNSIGNED NOT NULL,
  `date` varchar(20) NOT NULL,
  `item_1` int(1) NOT NULL,
  `item_2` int(1) NOT NULL,
  `item_3` int(1) NOT NULL,
  `item_4` int(1) NOT NULL,
  `item_5` int(1) NOT NULL,
  `item_6` int(1) NOT NULL,
  `item_7` int(1) NOT NULL,
  `item_8` int(1) NOT NULL,
  `item_9` int(1) NOT NULL,
  `item_10` int(1) NOT NULL,
  `item_11` int(1) NOT NULL,
  `item_12` int(1) NOT NULL,
  `item_13` int(1) NOT NULL,
  `item_14` int(1) NOT NULL,
  `mess` varchar(5220) CHARACTER SET utf8 COLLATE utf8_persian_ci DEFAULT NULL,
  `seen` tinyint(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_sa`
--

INSERT INTO `user_sa` (`id`, `date`, `item_1`, `item_2`, `item_3`, `item_4`, `item_5`, `item_6`, `item_7`, `item_8`, `item_9`, `item_10`, `item_11`, `item_12`, `item_13`, `item_14`, `mess`, `seen`) VALUES
(1, '1556946870', 2, 2, 2, 1, 1, 1, 1, 1, 3, 1, 1, 1, 2, 1, '', 0),
(2, '1557532749', 1, 1, 1, 1, 1, 1, 1, 2, 2, 1, 2, 1, 2, 2, '', 0),
(3, '1558280350', 1, 2, 1, 1, 1, 1, 1, 2, 3, 1, 2, 1, 2, 2, '', 0);

-- --------------------------------------------------------

--
-- Table structure for table `user_z`
--

CREATE TABLE `user_z` (
  `id` int(6) UNSIGNED NOT NULL,
  `date` varchar(20) NOT NULL,
  `item_1` int(1) NOT NULL,
  `item_2` int(1) NOT NULL,
  `item_3` int(1) NOT NULL,
  `item_4` int(1) NOT NULL,
  `item_5` int(1) NOT NULL,
  `item_6` int(1) NOT NULL,
  `item_7` int(1) NOT NULL,
  `item_8` int(1) NOT NULL,
  `item_9` int(1) NOT NULL,
  `item_10` int(1) NOT NULL,
  `item_11` int(1) NOT NULL,
  `item_12` int(1) NOT NULL,
  `item_13` int(1) NOT NULL,
  `item_14` int(1) NOT NULL,
  `mess` varchar(5220) CHARACTER SET utf8 COLLATE utf8_persian_ci DEFAULT NULL,
  `seen` tinyint(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_z`
--

INSERT INTO `user_z` (`id`, `date`, `item_1`, `item_2`, `item_3`, `item_4`, `item_5`, `item_6`, `item_7`, `item_8`, `item_9`, `item_10`, `item_11`, `item_12`, `item_13`, `item_14`, `mess`, `seen`) VALUES
(1, '1556391452', 1, 1, 1, 1, 1, 3, 3, 3, 3, 1, 3, 1, 1, 1, '10=به خاطر باشگاه فرصت نکردند', 1),
(2, '1556908766', 1, 1, 1, 2, 2, 1, 1, 1, 1, 1, 3, 2, 1, 2, '', 1),
(3, '1557518976', 2, 1, 1, 1, 2, 1, 1, 1, 1, 1, 3, 2, 1, 1, '', 0),
(4, '1558138878', 2, 1, 2, 1, 2, 2, 2, 2, 2, 1, 3, 2, 1, 1, '', 0);

-- --------------------------------------------------------

--
-- Table structure for table `user_zahra`
--

CREATE TABLE `user_zahra` (
  `id` int(6) UNSIGNED NOT NULL,
  `date` varchar(20) NOT NULL,
  `item_1` int(1) NOT NULL,
  `item_2` int(1) NOT NULL,
  `item_3` int(1) NOT NULL,
  `item_4` int(1) NOT NULL,
  `item_5` int(1) NOT NULL,
  `item_6` int(1) NOT NULL,
  `item_7` int(1) NOT NULL,
  `item_8` int(1) NOT NULL,
  `item_9` int(1) NOT NULL,
  `item_10` int(1) NOT NULL,
  `item_11` int(1) NOT NULL,
  `item_12` int(1) NOT NULL,
  `item_13` int(1) NOT NULL,
  `item_14` int(1) NOT NULL,
  `mess` varchar(5220) CHARACTER SET utf8 COLLATE utf8_persian_ci DEFAULT NULL,
  `seen` tinyint(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_zahra`
--

INSERT INTO `user_zahra` (`id`, `date`, `item_1`, `item_2`, `item_3`, `item_4`, `item_5`, `item_6`, `item_7`, `item_8`, `item_9`, `item_10`, `item_11`, `item_12`, `item_13`, `item_14`, `mess`, `seen`) VALUES
(1, '1558346654', 2, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 2, '4=گاهی میشورد', 1);

-- --------------------------------------------------------

--
-- Table structure for table `user_zohra`
--

CREATE TABLE `user_zohra` (
  `id` int(6) UNSIGNED NOT NULL,
  `date` varchar(20) NOT NULL,
  `item_1` int(1) NOT NULL,
  `item_2` int(1) NOT NULL,
  `item_3` int(1) NOT NULL,
  `item_4` int(1) NOT NULL,
  `item_5` int(1) NOT NULL,
  `item_6` int(1) NOT NULL,
  `item_7` int(1) NOT NULL,
  `item_8` int(1) NOT NULL,
  `item_9` int(1) NOT NULL,
  `item_10` int(1) NOT NULL,
  `item_11` int(1) NOT NULL,
  `item_12` int(1) NOT NULL,
  `item_13` int(1) NOT NULL,
  `item_14` int(1) NOT NULL,
  `mess` varchar(5220) CHARACTER SET utf8 COLLATE utf8_persian_ci DEFAULT NULL,
  `seen` tinyint(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_zohra`
--

INSERT INTO `user_zohra` (`id`, `date`, `item_1`, `item_2`, `item_3`, `item_4`, `item_5`, `item_6`, `item_7`, `item_8`, `item_9`, `item_10`, `item_11`, `item_12`, `item_13`, `item_14`, `mess`, `seen`) VALUES
(1, '1559334634', 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 1, 1, '0=میخواند ---q-v-d-z---1=بیدار میشود ---q-v-d-z---2=مرتب میکند---q-v-d-z---10=گاهی اوقات ', 0),
(2, '1560010931', 2, 1, 0, 1, 2, 0, 0, 0, 0, 1, 0, 0, 1, 1, '', 0),
(3, '1560666762', 1, 1, 1, 1, 2, 0, 0, 0, 0, 1, 1, 2, 1, 1, '', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admins`
--
ALTER TABLE `admins`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `mssg_s`
--
ALTER TABLE `mssg_s`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_a`
--
ALTER TABLE `user_a`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_ali`
--
ALTER TABLE `user_ali`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_ha`
--
ALTER TABLE `user_ha`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_m`
--
ALTER TABLE `user_m`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_massomeh`
--
ALTER TABLE `user_massomeh`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_s`
--
ALTER TABLE `user_s`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_sa`
--
ALTER TABLE `user_sa`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_z`
--
ALTER TABLE `user_z`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_zahra`
--
ALTER TABLE `user_zahra`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_zohra`
--
ALTER TABLE `user_zohra`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admins`
--
ALTER TABLE `admins`
  MODIFY `id` int(6) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `mssg_s`
--
ALTER TABLE `mssg_s`
  MODIFY `id` int(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(6) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=48;

--
-- AUTO_INCREMENT for table `user_a`
--
ALTER TABLE `user_a`
  MODIFY `id` int(6) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `user_ali`
--
ALTER TABLE `user_ali`
  MODIFY `id` int(6) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `user_ha`
--
ALTER TABLE `user_ha`
  MODIFY `id` int(6) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `user_m`
--
ALTER TABLE `user_m`
  MODIFY `id` int(6) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `user_massomeh`
--
ALTER TABLE `user_massomeh`
  MODIFY `id` int(6) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `user_s`
--
ALTER TABLE `user_s`
  MODIFY `id` int(6) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `user_sa`
--
ALTER TABLE `user_sa`
  MODIFY `id` int(6) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `user_z`
--
ALTER TABLE `user_z`
  MODIFY `id` int(6) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `user_zahra`
--
ALTER TABLE `user_zahra`
  MODIFY `id` int(6) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `user_zohra`
--
ALTER TABLE `user_zohra`
  MODIFY `id` int(6) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
