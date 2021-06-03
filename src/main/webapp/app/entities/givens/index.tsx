import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Givens from './givens';
import GivensDetail from './givens-detail';
import GivensUpdate from './givens-update';
import GivensDeleteDialog from './givens-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={GivensUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={GivensUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={GivensDetail} />
      <ErrorBoundaryRoute path={match.url} component={Givens} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={GivensDeleteDialog} />
  </>
);

export default Routes;
