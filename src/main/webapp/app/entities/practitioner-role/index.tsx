import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PractitionerRole from './practitioner-role';
import PractitionerRoleDetail from './practitioner-role-detail';
import PractitionerRoleUpdate from './practitioner-role-update';
import PractitionerRoleDeleteDialog from './practitioner-role-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PractitionerRoleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PractitionerRoleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PractitionerRoleDetail} />
      <ErrorBoundaryRoute path={match.url} component={PractitionerRole} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PractitionerRoleDeleteDialog} />
  </>
);

export default Routes;
