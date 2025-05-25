DO $$
DECLARE
i INT := 0;
    start_time TIMESTAMP;
    end_time TIMESTAMP;
    duration_us DOUBLE PRECISION;
BEGIN
FOR i IN 1..100 LOOP
        start_time := clock_timestamp();
PERFORM *
FROM Person.AddressType;


        end_time := clock_timestamp();
        duration_us := EXTRACT(EPOCH FROM (end_time - start_time)) * 1000000;

        RAISE NOTICE '%',  duration_us;
END LOOP;

END $$;