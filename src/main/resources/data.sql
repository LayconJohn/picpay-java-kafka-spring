/* REMOVE ALL*/
DELETE FROM TRANSACTION;

DELETE FROM WALLETS;

/*INSERT WALLET*/
INSERT INTO WALLET (
    ID,
    FULL_NAME,
    CPF,
    EMAIL,
    "PASSWORD",
    "TYPE",
    BALANCE 
) VALUES (
    1, 'Laycon John', 07535185541, 'layconjohn@email.com', '123456', 1, 1000.00
);

INSERT INTO WALLET (
    ID,
    FULL_NAME,
    CPF,
    EMAIL,
    "PASSWORD",
    "TYPE",
    BALANCE 
) VALUES (
    2, 'John', 04135185575, 'johnlaycon@email.com', '123456', 2, 10000.00
);