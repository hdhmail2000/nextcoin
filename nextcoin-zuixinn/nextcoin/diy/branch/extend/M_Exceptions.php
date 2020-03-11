<?php

/**
 * Exceptions Class
 */
class M_Exceptions extends CI_Exceptions {



    // --------------------------------------------------------------------

    /**
     * 404 Error Handler
     *
     * @uses	CI_Exceptions::show_error()
     *
     * @param	string	$page		Page URI
     * @param 	bool	$log_error	Whether to log the error
     * @return	void
     */
    public function show_404($page = '', $log_error = TRUE)
    {
        if (is_cli())
        {
            $heading = 'Not Found';
            $message = 'The controller/method pair you requested was not found.';
        }
        else
        {
            $heading = '404';
            $message = '当前访问的页面不存在';
        }

        // By default we log this, but allow a dev to skip it
        if ($log_error)
        {
            $pageURL = 'http';
            if ((isset($_SERVER['HTTPS']) && $_SERVER['HTTPS'] == 'on')
                || (isset($_SERVER['SERVER_PORT']) && $_SERVER['SERVER_PORT'] == '443')) {
                $pageURL.= 's';
            }

            $pageURL.= '://';
            if (strpos($_SERVER['HTTP_HOST'], ':') !== FALSE) {
                $url = explode(':', $_SERVER['HTTP_HOST']);
                $url[0] ? $pageURL.= $_SERVER['HTTP_HOST'] : $pageURL.= $url[0];
            } else {
                $pageURL.= $_SERVER['HTTP_HOST'];
            }

            $pageURL.= $_SERVER['REQUEST_URI'] ? $_SERVER['REQUEST_URI'] : $_SERVER['PHP_SELF'];
            log_message('error', $heading.': '.$page.'（'.$pageURL.'）');
        }

        echo $this->show_error($heading, $message, 'error_404', 404);
        exit(4); // EXIT_UNKNOWN_FILE
    }


}
