<?php
namespace Utils;

class Constants 
{
    /* GLOBAL CONFIG */
    const DATE_FORMAT = "Y-m-d H:i:s";
    
    /* GLOBAL PARAMETERS */
    const PAR_PAGE = "page";
    const PAR_TEAM = "slug";
    const PAR_AJAX = "ajax_action";
    const PAR_ACCOUNTNUMBER = "accnumber";
    const PAR_BEGINDATE = "begindate";
    const PAR_ENDDATE = "enddate";
    const PAR_AMOUNT = "importe";
    const PAR_DESCRIPTION = "descripcion";
    const PAR_FORCE = "forzar";
    const PAR_DNI = "dni";
    const DEFAULT_AJAX_ERROR_DESC = "Error no especificado.";
    const DEFAULT_AJAX_ERROR_ACTIONNOTFOUND = "No se ha encontrado la acción especificada: {action}";
}