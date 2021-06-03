import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import HumanName from './human-name';
import HumanNameDetail from './human-name-detail';
import HumanNameUpdate from './human-name-update';
import HumanNameDeleteDialog from './human-name-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={HumanNameUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={HumanNameUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={HumanNameDetail} />
      <ErrorBoundaryRoute path={match.url} component={HumanName} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={HumanNameDeleteDialog} />
  </>
);

export default Routes;
