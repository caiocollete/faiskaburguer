@echo off
:menu
echo.
echo Selecione uma opcao:
echo =====================
echo 1. Status do Servico
echo 2. Iniciar Servico
echo 3. Parar Servico
echo 4. Sair
echo.

set /p option="Escolha uma opcao (1-4): "
cls
if "%option%"=="1" goto status
if "%option%"=="2" goto start
if "%option%"=="3" goto stop
if "%option%"=="4" goto exit

:status
echo Verificando o status do servico "postgresql-x64-17"...
sc query "postgresql-x64-17" | findstr /I "STATE"
goto menu

:start
echo Iniciando o servico "postgresql-x64-17"...
sc start "postgresql-x64-17"
goto menu

:stop
echo Parando o servico "postgresql-x64-17"...
sc stop "postgresql-x64-17"
goto menu

:exit
echo Saindo...
exit
