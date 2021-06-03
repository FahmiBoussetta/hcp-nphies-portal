import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ListRoleCodeEnum from './list-role-code-enum';
import ListRoleCodeEnumDetail from './list-role-code-enum-detail';
import ListRoleCodeEnumUpdate from './list-role-code-enum-update';
import ListRoleCodeEnumDeleteDialog from './list-role-code-enum-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ListRoleCodeEnumUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ListRoleCodeEnumUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ListRoleCodeEnumDetail} />
      <ErrorBoundaryRoute path={match.url} component={ListRoleCodeEnum} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ListRoleCodeEnumDeleteDialog} />
  </>
);

export default Routes;
