-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Anamakine: 127.0.0.1
-- Üretim Zamanı: 11 May 2021, 01:49:45
-- Sunucu sürümü: 10.4.18-MariaDB
-- PHP Sürümü: 7.3.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Veritabanı: `spotify`
--

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_turkish_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_turkish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_turkish_ci;

--
-- Tablo döküm verisi `admin`
--

INSERT INTO `admin` (`id`, `email`, `password`) VALUES
(1, 'admin@gmail.com', 'admin123'),
(3, 'b', 'b');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `album`
--

CREATE TABLE `album` (
  `id` int(11) NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_turkish_ci NOT NULL,
  `artistID` int(11) NOT NULL,
  `typeID` int(11) NOT NULL,
  `date` varchar(255) COLLATE utf8mb4_turkish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_turkish_ci;

--
-- Tablo döküm verisi `album`
--

INSERT INTO `album` (`id`, `name`, `artistID`, `typeID`, `date`) VALUES
(7, 'Albüm3', 1, 1, '24.05.2021'),
(8, 'Albüm4', 10, 1, '11.05.2021');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `albümi̇çerik`
--

CREATE TABLE `albümi̇çerik` (
  `id` int(11) NOT NULL,
  `sarkıID` int(11) NOT NULL,
  `albumID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_turkish_ci;

--
-- Tablo döküm verisi `albümi̇çerik`
--

INSERT INTO `albümi̇çerik` (`id`, `sarkıID`, `albumID`) VALUES
(3, 101, 7);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `artist`
--

CREATE TABLE `artist` (
  `id` int(255) NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_turkish_ci NOT NULL,
  `country` varchar(255) COLLATE utf8mb4_turkish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_turkish_ci;

--
-- Tablo döküm verisi `artist`
--

INSERT INTO `artist` (`id`, `name`, `country`) VALUES
(1, 'Mustafa Sandal', 'Turkey'),
(2, 'Karsu', 'Turkey'),
(3, 'Fazıl Say', 'Turkey'),
(4, 'Bülent Ortaçgil', 'Turkey'),
(5, 'Hadise', 'Turkey'),
(6, 'Mozart', 'Avusturya'),
(7, 'Jehan Barbur', 'Turkey'),
(8, 'Yalın', 'Turkey'),
(9, 'Antonio Vivaldi', 'Italy'),
(10, 'Murat Boz', 'Turkey');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `follower`
--

CREATE TABLE `follower` (
  `id` int(11) NOT NULL,
  `takipEdenID` int(11) NOT NULL,
  `takipEdilenID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_turkish_ci;

--
-- Tablo döküm verisi `follower`
--

INSERT INTO `follower` (`id`, `takipEdenID`, `takipEdilenID`) VALUES
(4, 3, 10),
(5, 10, 3),
(7, 10, 1),
(9, 10, 4),
(11, 10, 10);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `music`
--

CREATE TABLE `music` (
  `id` int(255) NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_turkish_ci NOT NULL,
  `typeId` int(255) NOT NULL,
  `singerId` int(255) NOT NULL,
  `date` varchar(255) COLLATE utf8mb4_turkish_ci NOT NULL,
  `süre` varchar(255) COLLATE utf8mb4_turkish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_turkish_ci;

--
-- Tablo döküm verisi `music`
--

INSERT INTO `music` (`id`, `name`, `typeId`, `singerId`, `date`, `süre`) VALUES
(79, 'Adını Bilen Yazsın', 1, 10, '24.05.2018', '3.55'),
(80, 'Janti', 1, 10, '09.11.2016', '4.10'),
(81, 'Aya Benzer', 1, 1, '23.09.2020', '2.59'),
(83, 'Düm Tek Tek', 1, 5, '03.05.2010', '4.21'),
(84, 'Olsun', 1, 5, '15.03.2021', '4.21'),
(85, 'Şampiyon', 1, 5, '02.02.2018', '3.55'),
(86, 'Yeniden', 1, 8, '04.05.2014', '3.21'),
(87, 'Benimki', 1, 8, '07.07.2015', '3.52'),
(88, 'Siyah', 2, 2, '08.10.2018', '5.13'),
(89, 'Bırak Beni Böyle', 2, 2, '06.06.2018', '3.12'),
(90, 'İnci Tanem', 2, 2, '01.01.2018', '4.00'),
(91, 'Bu Su Hiç Durmaz', 2, 4, '05.07.2013', '5.42'),
(92, 'Olmalı Mı Olmamalı Mı?', 2, 4, '20.12.1974', '2.06'),
(93, 'Eylül Akşamı', 2, 4, '14.04.2019', '6.20'),
(94, 'Gidersen', 2, 7, '23.02.2010', '3.12'),
(95, 'Say : Black Earth', 3, 3, '09.09.2019', '9.06'),
(96, 'İnsan İnsan', 3, 3, '04.04.2016', '6.31'),
(97, 'Nazım Oratoryosu', 3, 3, '01.05.2019', '1.25.00'),
(98, 'Türk Marşı', 3, 6, '05.10.1783', '3.55'),
(99, 'Lacrimosa', 3, 6, '04.05.1790', '3.20'),
(100, 'Requiem', 3, 6, '29.12.1785', '8.53'),
(101, 'Araba', 1, 1, '07.01.2015', '2.45'),
(102, 'Düm Tek Tek', 1, 5, '03.05.2010', '4.21'),
(103, 'Benimki', 1, 8, '07.07.2015', '3.52'),
(104, 'Olsun', 1, 5, '15.03.2021', '4.21'),
(105, 'Yeniden', 1, 8, '04.05.2014', '3.21'),
(106, 'İnci Tanem', 2, 2, '01.01.2018', '4.00'),
(107, 'Olmalı Mı Olmamalı Mı?', 2, 4, '20.12.1974', '2.06'),
(108, 'Gece', 1, 10, '20.05.2020', '3.45');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `playlist`
--

CREATE TABLE `playlist` (
  `id` int(11) NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_turkish_ci NOT NULL,
  `typeID` int(255) NOT NULL,
  `userID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_turkish_ci;

--
-- Tablo döküm verisi `playlist`
--

INSERT INTO `playlist` (`id`, `name`, `typeID`, `userID`) VALUES
(1, 'POP', 1, 3),
(2, 'CAZZ', 2, 3),
(3, 'KLASİK', 3, 3),
(9, 'POP', 1, 10),
(10, 'CAZZ', 2, 10),
(11, 'KLASİK', 3, 10),
(12, 'POP', 1, 4),
(13, 'CAZZ', 2, 4),
(14, 'KLASİK', 3, 4),
(15, 'POP', 1, 1),
(16, 'CAZZ', 2, 1),
(17, 'KLASİK', 3, 1),
(18, 'POP', 1, 6),
(19, 'CAZZ', 2, 6),
(20, 'KLASİK', 3, 6);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `playlisti̇çerik`
--

CREATE TABLE `playlisti̇çerik` (
  `id` int(11) NOT NULL,
  `sarkıID` int(11) NOT NULL,
  `playlistID` int(11) NOT NULL,
  `dinlenme` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_turkish_ci;

--
-- Tablo döküm verisi `playlisti̇çerik`
--

INSERT INTO `playlisti̇çerik` (`id`, `sarkıID`, `playlistID`, `dinlenme`) VALUES
(1, 101, 9, 1),
(4, 104, 9, 0),
(7, 106, 10, 0),
(8, 94, 10, 2),
(9, 99, 11, 0),
(10, 89, 10, 1),
(11, 79, 1, 0),
(12, 102, 1, 0),
(13, 104, 1, 0),
(14, 105, 1, 0),
(15, 107, 2, 0),
(16, 94, 2, 0),
(17, 95, 3, 1),
(18, 97, 3, 0),
(19, 99, 3, 0),
(20, 101, 1, 0),
(21, 104, 9, 0),
(23, 97, 11, 0),
(24, 83, 9, 0),
(25, 102, 9, 0);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `type`
--

CREATE TABLE `type` (
  `id` int(255) NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_turkish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_turkish_ci;

--
-- Tablo döküm verisi `type`
--

INSERT INTO `type` (`id`, `name`) VALUES
(1, 'Pop'),
(2, 'Cazz'),
(3, 'Klasik');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `user`
--

CREATE TABLE `user` (
  `id` int(255) NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_turkish_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_turkish_ci NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_turkish_ci NOT NULL,
  `type` varchar(255) COLLATE utf8mb4_turkish_ci NOT NULL,
  `odendiMi` tinyint(1) NOT NULL,
  `country` varchar(255) COLLATE utf8mb4_turkish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_turkish_ci;

--
-- Tablo döküm verisi `user`
--

INSERT INTO `user` (`id`, `name`, `password`, `email`, `type`, `odendiMi`, `country`) VALUES
(1, 'Murat Karakurt', 'murat444', 'mrtkrkrt3444@hotmail.com', 'Premium', 0, 'Turkey'),
(3, 'Burhan', 'burhan123', 'burhan@gmail.com', 'Premium', 0, 'Turkey'),
(4, 'Bahadır', 'taka123', 'bahadır@gmail.com', 'Premium', 0, 'Turkey'),
(6, 'Buğra', 'bugra123', 'bugra@gmail.com', 'Normal', 0, 'Turkey'),
(10, 'a', 'a', 'a', 'Premium', 0, 'Turkey');

--
-- Dökümü yapılmış tablolar için indeksler
--

--
-- Tablo için indeksler `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `album`
--
ALTER TABLE `album`
  ADD PRIMARY KEY (`id`),
  ADD KEY `artKey` (`artistID`),
  ADD KEY `tipID` (`typeID`);

--
-- Tablo için indeksler `albümi̇çerik`
--
ALTER TABLE `albümi̇çerik`
  ADD PRIMARY KEY (`id`),
  ADD KEY `şarkı` (`sarkıID`),
  ADD KEY `albüm` (`albumID`);

--
-- Tablo için indeksler `artist`
--
ALTER TABLE `artist`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `follower`
--
ALTER TABLE `follower`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `music`
--
ALTER TABLE `music`
  ADD PRIMARY KEY (`id`),
  ADD KEY `artistKey` (`singerId`),
  ADD KEY `typeKey` (`typeId`);

--
-- Tablo için indeksler `playlist`
--
ALTER TABLE `playlist`
  ADD PRIMARY KEY (`id`),
  ADD KEY `typeID` (`typeID`),
  ADD KEY `userID` (`userID`);

--
-- Tablo için indeksler `playlisti̇çerik`
--
ALTER TABLE `playlisti̇çerik`
  ADD PRIMARY KEY (`id`),
  ADD KEY `sarkıID` (`sarkıID`),
  ADD KEY `palylistID` (`playlistID`);

--
-- Tablo için indeksler `type`
--
ALTER TABLE `type`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Dökümü yapılmış tablolar için AUTO_INCREMENT değeri
--

--
-- Tablo için AUTO_INCREMENT değeri `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Tablo için AUTO_INCREMENT değeri `album`
--
ALTER TABLE `album`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Tablo için AUTO_INCREMENT değeri `albümi̇çerik`
--
ALTER TABLE `albümi̇çerik`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Tablo için AUTO_INCREMENT değeri `artist`
--
ALTER TABLE `artist`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Tablo için AUTO_INCREMENT değeri `follower`
--
ALTER TABLE `follower`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Tablo için AUTO_INCREMENT değeri `music`
--
ALTER TABLE `music`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=109;

--
-- Tablo için AUTO_INCREMENT değeri `playlist`
--
ALTER TABLE `playlist`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- Tablo için AUTO_INCREMENT değeri `playlisti̇çerik`
--
ALTER TABLE `playlisti̇çerik`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- Tablo için AUTO_INCREMENT değeri `type`
--
ALTER TABLE `type`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Tablo için AUTO_INCREMENT değeri `user`
--
ALTER TABLE `user`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Dökümü yapılmış tablolar için kısıtlamalar
--

--
-- Tablo kısıtlamaları `album`
--
ALTER TABLE `album`
  ADD CONSTRAINT `artKey` FOREIGN KEY (`artistID`) REFERENCES `artist` (`id`),
  ADD CONSTRAINT `tipID` FOREIGN KEY (`typeID`) REFERENCES `type` (`id`);

--
-- Tablo kısıtlamaları `albümi̇çerik`
--
ALTER TABLE `albümi̇çerik`
  ADD CONSTRAINT `albüm` FOREIGN KEY (`albumID`) REFERENCES `album` (`id`),
  ADD CONSTRAINT `şarkı` FOREIGN KEY (`sarkıID`) REFERENCES `music` (`id`);

--
-- Tablo kısıtlamaları `music`
--
ALTER TABLE `music`
  ADD CONSTRAINT `artistKey` FOREIGN KEY (`singerId`) REFERENCES `artist` (`id`),
  ADD CONSTRAINT `typeKey` FOREIGN KEY (`typeId`) REFERENCES `type` (`id`);

--
-- Tablo kısıtlamaları `playlist`
--
ALTER TABLE `playlist`
  ADD CONSTRAINT `typeID` FOREIGN KEY (`typeID`) REFERENCES `type` (`id`),
  ADD CONSTRAINT `userID` FOREIGN KEY (`userID`) REFERENCES `user` (`id`);

--
-- Tablo kısıtlamaları `playlisti̇çerik`
--
ALTER TABLE `playlisti̇çerik`
  ADD CONSTRAINT `palylistID` FOREIGN KEY (`playlistID`) REFERENCES `playlist` (`id`),
  ADD CONSTRAINT `sarkıID` FOREIGN KEY (`sarkıID`) REFERENCES `music` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
