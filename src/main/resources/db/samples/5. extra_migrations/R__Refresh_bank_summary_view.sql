-- Recreate or refresh a summary view for bank data
CREATE OR REPLACE VIEW bank_summary_view AS
SELECT
    name,
    COUNT(*) AS branch_count,
    MIN(established_year) AS oldest_branch_year
FROM bank
GROUP BY name;
