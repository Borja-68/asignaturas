select art_codigo,art_nome, art_peso,prv_nome from artigos join provedores 
on prv_id=art_provedor where art_color="negro";

-- Mostrar para cada venta: nombre y apellidos del cliente y fecha de venta.

select clt_nome,clt_apelidos,ven_data from vendas join clientes on ven_cliente=clt_id;

-- Mostrar: número total de ventas, número de artículos vendidos, suma de unidades
-- vendidas y la media de los precios de los artículos vendidos.

select count(distinct ven_id),count(distinct dev_artigo),sum(dev_cantidade),avg(dev_prezo_unitario) from detalle_vendas join vendas on ven_id=dev_venda;

select ar.art_codigo, ar.art_nome, ar.art_peso, pe.peso_nome
from artigos as ar inner join pesos as pe on ar.art_peso between peso_min and peso_max
order by ar.art_peso;

select ven_id,ven_data,concat(clt_nome,",",clt_apelidos),concat(emp_nome,",",emp_apelidos)from 
clientes join vendas on ven_cliente=clt_id join empregados on ven_empregado=emp_id order by clt_apelidos,clt_nome desc;

select clt_id,clt_apelidos,clt_nome,if(ven_data is null, "SIN COMPRAS",ven_data) from vendas right join clientes on clt_id=ven_cliente where clt_id<=10;

select emp_id,emp_apelidos,emp_nome,if(ven_id is not null,"Si","No") from vendas right join empregados on emp_id=ven_id;

select art_codigo,art_pc from artigos where art_pc>(select art_pc from artigos where art_codigo="0713242") order by 2 desc;

select art_codigo,art_pc,art_pv from artigos where art_pc>(select avg(art_pc) from artigos);
