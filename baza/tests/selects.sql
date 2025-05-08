DO $$
DECLARE
i INTEGER := 1;
    start_time TIMESTAMP;
    end_time TIMESTAMP;
    elapsed INTERVAL;
BEGIN
    WHILE i <= 100 LOOP
        start_time := clock_timestamp();

        -- Replace the query below with yours
        PERFORM
p.businessentityid,
            p.firstname,
            p.lastname,
            b.rowguid,
            b.modifieddate
        FROM person p
        JOIN businessentity b ON p.businessentityid = b.businessentityid
        WHERE b.businessentityid = 1;

        end_time := clock_timestamp();
        elapsed := end_time - start_time;

        RAISE NOTICE 'Execution % took: %', i, elapsed;

        i := i + 1;
END LOOP;
END;
$$ LANGUAGE plpgsql;
