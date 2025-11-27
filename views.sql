-- Materialized view: Books per Author
-- Refreshed every hour via @Scheduled task
create materialized view books_per_author as
select a.id        as author_id,
       count(b.id) as num_books
from authors a
         left join
     books b on b.author_id = a.id
group by a.id;

-- Materialized view: Authors per Country
-- Refreshed on author create/update/delete via events
create materialized view authors_per_country as
select c.id        as country_id,
       count(a.id) as num_authors
from countries c
         left join
     authors a on a.country_id = c.id
group by c.id;
