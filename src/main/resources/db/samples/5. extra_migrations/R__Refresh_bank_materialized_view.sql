-- Example of creating or refreshing a materialized view for banks
CREATE OR REPLACE MATERIALIZED VIEW mv_bank_summary AS
SELECT
    name,
    COUNT(*) AS total_branches
FROM bank
GROUP BY name;
