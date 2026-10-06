package org.oscelot.jshack.stripes;

import java.util.Set;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.controller.NameBasedActionResolver;

/**
 * ActionResolver with a fixed bean list, selected by the ActionResolver.Class init-param
 * in web.xml. Stripes' package scan reads every class file as text looking for
 * directories, which Blackboard's Tomcat rejects hard enough to kill StripesFilter.init().
 *
 * <p>Any new ActionBean has to be added here or it will not be routable.
 * DefaultViewActionBean is absent because NameBasedActionResolver registers it itself.
 */
public class StaticActionResolver extends NameBasedActionResolver {

    @Override
    protected Set<Class<? extends ActionBean>> findClasses() {
        return Set.<Class<? extends ActionBean>>of(
                ConfigAction.class,
                CreateHackAction.class,
                DeleteHackAction.class,
                DownloadHackPackageAction.class,
                ReloadHackPackagesAction.class,
                SetHackStatusAction.class,
                SuspendInjectionAction.class,
                UploadHackPackageAction.class);
    }
}
