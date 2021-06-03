import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Total from './total';
import TotalDetail from './total-detail';
import TotalUpdate from './total-update';
import TotalDeleteDialog from './total-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TotalUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TotalUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TotalDetail} />
      <ErrorBoundaryRoute path={match.url} component={Total} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TotalDeleteDialog} />
  </>
);

export default Routes;
