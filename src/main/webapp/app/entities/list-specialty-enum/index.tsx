import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ListSpecialtyEnum from './list-specialty-enum';
import ListSpecialtyEnumDetail from './list-specialty-enum-detail';
import ListSpecialtyEnumUpdate from './list-specialty-enum-update';
import ListSpecialtyEnumDeleteDialog from './list-specialty-enum-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ListSpecialtyEnumUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ListSpecialtyEnumUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ListSpecialtyEnumDetail} />
      <ErrorBoundaryRoute path={match.url} component={ListSpecialtyEnum} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ListSpecialtyEnumDeleteDialog} />
  </>
);

export default Routes;
