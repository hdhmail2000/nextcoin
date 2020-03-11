<?php

function &load_class($class, $directory = 'libraries', $param = NULL)
{
    static $_classes = array();

    // Does the class exist? If so, we're done...
    if (isset($_classes[$class]))
    {
        return $_classes[$class];
    }

    $name = FALSE;

    // Look for the class first in the local application/libraries folder
    // then in the native system/libraries folder
    foreach (array(APPPATH, BASEPATH) as $path)
    {
        if (file_exists($path.$directory.'/'.$class.'.php'))
        {
            $name = 'CI_'.$class;

            if (class_exists($name, FALSE) === FALSE)
            {
                require_once($path.$directory.'/'.$class.'.php');
            }

            break;
        }
    }

    // Is the request a class extension? If so we load it too
    if (file_exists(APPPATH.$directory.'/'.config_item('subclass_prefix').$class.'.php'))
    {
        $name = config_item('subclass_prefix').$class;

        if (class_exists($name, FALSE) === FALSE)
        {
            require_once(APPPATH.$directory.'/'.$name.'.php');
        }
    } elseif ($directory == 'core' && is_file(FCPATH.'branch/extend/M_'.$class.'.php')) {
        $name = config_item('subclass_prefix').$class;
        if (class_exists($name, FALSE) === FALSE)
        {
            require_once(FCPATH.'branch/extend/M_'.$class.'.php');
        }
    } else {
        $name = 'CI_'.$class;
    }
    // Did we find the class?
    if (class_exists($name, FALSE) === FALSE)
    {
        // Note: We use exit() rather than show_error() in order to avoid a
        // self-referencing loop with the Exceptions class
        set_status_header(503);
        echo 'Unable to locate the specified class: '.$name.'.php';
        exit(5); // EXIT_UNK_CLASS
    }
    // Keep track of what we just loaded
    is_loaded($class);

    $_classes[$class] = isset($param)
        ? new $name($param)
        : new $name();
    return $_classes[$class];
}

function &get_config(Array $replace = array())
{
    static $config;

    if (empty($config))
    {
        require WEBPATH.'config/config.php';
    }

    // Are any values being dynamically added or replaced?
    foreach ($replace as $key => $val)
    {
        $config[$key] = $val;
    }

    return $config;
}

function &get_mimes()
{
    static $_mimes;

    if (empty($_mimes))
    {
        /*
        if (file_exists(APPPATH.'config/'.ENVIRONMENT.'/mimes.php'))
        {
            $_mimes = include(APPPATH.'config/'.ENVIRONMENT.'/mimes.php');
        }
        else*/
        if (file_exists(WEBPATH.'config/mimes.php'))
        {
            $_mimes = include(WEBPATH.'config/mimes.php');
        }
        else
        {
            $_mimes = array();
        }
    }

    return $_mimes;
}