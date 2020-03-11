<?php

if (!defined('BASEPATH')) exit('No direct script access allowed');

class M_Loader extends CI_Loader {

    /**
     * Initializer
     *
     * @todo	Figure out a way to move this to the constructor
     *		without breaking *package_path*() methods.
     * @uses	CI_Loader::_ci_autoloader()
     * @used-by	CI_Controller::__construct()
     * @return	void
     */
    public function initialize()
    {
        $this->_ci_library_paths =	array(FCPATCH.'dayrui/',APPPATH, BASEPATH);
        $this->_ci_model_paths =	array(FCPATCH.'dayrui/',APPPATH);
        $this->_ci_helper_paths =	array(FCPATCH.'dayrui/',APPPATH, BASEPATH);
        $this->_ci_autoloader();
    }

    /**
     * Internal CI Library Instantiator
     *
     * @used-by	CI_Loader::_ci_load_stock_library()
     * @used-by	CI_Loader::_ci_load_library()
     *
     * @param	string		$class		Class name
     * @param	string		$prefix		Class name prefix
     * @param	array|null|bool	$config		Optional configuration to pass to the class constructor:
     *						FALSE to skip;
     *						NULL to search in config paths;
     *						array containing configuration data
     * @param	string		$object_name	Optional object name to assign to
     * @return	void
     */
    protected function _ci_init_library($class, $prefix, $config = FALSE, $object_name = NULL)
    {
        // Is there an associated config file for this class? Note: these should always be lowercase
        if ($config === NULL)
        {
            // Fetch the config paths containing any package paths
            $config_component = $this->_ci_get_component('config');

            if (is_array($config_component->_config_paths))
            {
                $found = FALSE;
                foreach ($config_component->_config_paths as $path)
                {
                    // We test for both uppercase and lowercase, for servers that
                    // are case-sensitive with regard to file names. Load global first,
                    // override with environment next
                    if (is_file($path.'config/'.strtolower($class).'.php'))
                    {
                        include($path.'config/'.strtolower($class).'.php');
                        $found = TRUE;
                    }
                    elseif (is_file($path.'config/'.ucfirst(strtolower($class)).'.php'))
                    {
                        include($path.'config/'.ucfirst(strtolower($class)).'.php');
                        $found = TRUE;
                    }


                    // Break on the first found configuration, thus package
                    // files are not overridden by default paths
                    if ($found === TRUE)
                    {
                        break;
                    }
                }
            }
        }

        $class_name = $prefix.$class;

        // Is the class name valid?
        if ( ! class_exists($class_name, FALSE))
        {
            log_message('error', 'Non-existent class: '.$class_name);
            show_error('Non-existent class: '.$class_name);
        }

        // Set the variable name we will assign the class to
        // Was a custom class name supplied? If so we'll use it
        if (empty($object_name))
        {
            $object_name = strtolower($class);
            if (isset($this->_ci_varmap[$object_name]))
            {
                $object_name = $this->_ci_varmap[$object_name];
            }
        }

        // Don't overwrite existing properties
        $CI =& get_instance();
        if (isset($CI->$object_name))
        {
            if ($CI->$object_name instanceof $class_name)
            {
                log_message('debug', $class_name." has already been instantiated as '".$object_name."'. Second attempt aborted.");
                return;
            }

            show_error("Resource '".$object_name."' already exists and is not a ".$class_name." instance.");
        }

        // Save the class name and object name
        $this->_ci_classes[$object_name] = $class;

        // Instantiate the class
        $CI->$object_name = isset($config)
            ? new $class_name($config)
            : new $class_name();
    }

    // --------------------------------------------------------------------


    /**
     * CI Autoloader
     *
     * Loads component listed in the config/autoload.php file.
     *
     * @used-by	CI_Loader::initialize()
     * @return	void
     */
    protected function _ci_autoloader()
    {
        include(WEBPATH.'config/autoload.php');

        if ( ! isset($autoload))
        {
            return;
        }

        // Autoload packages
        if (isset($autoload['packages']))
        {
            foreach ($autoload['packages'] as $package_path)
            {
                $this->add_package_path($package_path);
            }
        }

        // Load any custom config file
        if (count($autoload['config']) > 0)
        {
            foreach ($autoload['config'] as $val)
            {
                $this->config($val);
            }
        }

        // Autoload helpers and languages
        foreach (array('helper', 'language') as $type)
        {
            if (isset($autoload[$type]) && count($autoload[$type]) > 0)
            {
                $this->$type($autoload[$type]);
            }
        }

        // Autoload drivers
        if (isset($autoload['drivers']))
        {
            $this->driver($autoload['drivers']);
        }

        // Load libraries
        if (isset($autoload['libraries']) && count($autoload['libraries']) > 0)
        {
            // Load the database driver.
            if (in_array('database', $autoload['libraries']))
            {
                $this->database();
                $autoload['libraries'] = array_diff($autoload['libraries'], array('database'));
            }

            // Load all other libraries
            $this->library($autoload['libraries']);
        }

        // Autoload models
        if (isset($autoload['model']))
        {
            $this->model($autoload['model']);
        }
    }

    /**
     * Model Loader
     *
     * Loads and instantiates models.
     *
     * @param	string	$model		Model name
     * @param	string	$name		An optional object name to assign to
     * @param	bool	$db_conn	An optional database connection configuration to initialize
     * @return	object
     */
    public function model($model, $name = '', $db_conn = FALSE)
    {
        if (empty($model))
        {
            return $this;
        }
        elseif (is_array($model))
        {
            foreach ($model as $key => $value)
            {
                is_int($key) ? $this->model($value, '', $db_conn) : $this->model($key, $value, $db_conn);
            }

            return $this;
        }

        $path = '';

        // Is the model in a sub-folder? If so, parse out the filename and path.
        if (($last_slash = strrpos($model, '/')) !== FALSE)
        {
            // The path is in front of the last slash
            $path = substr($model, 0, ++$last_slash);

            // And the model name behind it
            $model = substr($model, $last_slash);
        }

        if (empty($name))
        {
            $name = $model;
        }

        if (in_array($name, $this->_ci_models, TRUE))
        {
            return $this;
        }

        $CI =& get_instance();
        if (isset($CI->$name))
        {
            throw new RuntimeException('The model name you are loading is the name of a resource that is already being used: '.$name);
        }

        if ($db_conn !== FALSE && ! class_exists('CI_DB', FALSE))
        {
            if ($db_conn === TRUE)
            {
                $db_conn = '';
            }

            $this->database($db_conn, FALSE, TRUE);
        }

        // Note: All of the code under this condition used to be just:
        //
        //       load_class('Model', 'core');
        //
        //       However, load_class() instantiates classes
        //       to cache them for later use and that prevents
        //       MY_Model from being an abstract class and is
        //       sub-optimal otherwise anyway.
        if ( ! class_exists('CI_Model', FALSE))
        {
            $app_path = APPPATH.'core'.DIRECTORY_SEPARATOR;
            if (file_exists($app_path.'Model.php'))
            {
                require_once($app_path.'Model.php');
                if ( ! class_exists('CI_Model', FALSE))
                {
                    throw new RuntimeException($app_path."Model.php exists, but doesn't declare class CI_Model");
                }
            }
            elseif ( ! class_exists('CI_Model', FALSE))
            {
                require_once(FCPATH.'branch/extend/M_Model.php');
            }


        }

        $model = ucfirst($model);
        if ( ! class_exists($model, FALSE))
        {
            foreach ($this->_ci_model_paths as $mod_path)
            {
                if ( ! file_exists($mod_path.'models/'.$path.$model.'.php'))
                {
                    continue;
                }

                require_once($mod_path.'models/'.$path.$model.'.php');
                if ( ! class_exists($model, FALSE))
                {
                    throw new RuntimeException($mod_path."models/".$path.$model.".php exists, but doesn't declare class ".$model);
                }

                break;
            }

            if ( ! class_exists($model, FALSE))
            {
                throw new RuntimeException('Unable to locate the model you have specified: '.$model);
            }
        }
        elseif ( ! is_subclass_of($model, 'CI_Model'))
        {
            throw new RuntimeException("Class ".$model." already exists and doesn't extend CI_Model");
        }

        $this->_ci_models[] = $name;
        $CI->$name = new $model();
        return $this;
    }



}