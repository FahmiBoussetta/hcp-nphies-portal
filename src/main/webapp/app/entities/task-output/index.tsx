import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TaskOutput from './task-output';
import TaskOutputDetail from './task-output-detail';
import TaskOutputUpdate from './task-output-update';
import TaskOutputDeleteDialog from './task-output-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TaskOutputUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TaskOutputUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TaskOutputDetail} />
      <ErrorBoundaryRoute path={match.url} component={TaskOutput} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TaskOutputDeleteDialog} />
  </>
);

export default Routes;
