/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  dina
 * Created: 13 nov. 2022
 */

create table Vehicule(
id serial not null primary key,
nomChauffeur varchar(50) not null,
matricule varchar(50) not null);

create table kilometrage(
id serial not null primary key,
vehiculeId serial not null,
foreign key (vehiculeId)references Vehicule(id),
daty date default current_date,
debut double precision  default 0,
fin double precision  default 0
);
insert into Vehicule(nomChauffeur,matricule)values('Rabe','1234ATD');
insert into Vehicule(nomChauffeur,matricule)values('Rakoto','4309TUN');
insert into Vehicule(nomChauffeur,matricule)values('Ravao','1238VOS');

insert into kilometrage(vehiculeId,daty,debut,fin)values(1,'2022-11-12',12,30);
create table Utilisateur(
id serial primary key,
nom varchar(40) not null,
login varchar(50) not null,
pwd  varchar(50) not null);
create table Tokens(
utilisateurId int,
foreign key (utilisateurId)references Utilisateur(id),
token varchar(250) not null,
dateExp date default current_date);

create table Assurance(id serial primary key,
vehiculeId int references Vehicule(id),
datePaie date default current_date,
dateExp date default current_date,
montant double precision default 0
);
select *, date(dateexp)-date(datepaie) as durer,case when ((date(dateexp)-date(datepaie) as durer)>30) then 'Exp' else 'Non Exp' end etat from assurance ass join vehicule vs on vs.id=ass.vehiculeid

create or replace view assurance_vehicule
as select ve.*,date(dateexp)-date(current_date)as jour_restant
 from vehicule ve join assurance ass on ass.vehiculeid=ve.id;
