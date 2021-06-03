import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TaskResponse from './task-response';
import TaskResponseDetail from './task-response-detail';
import TaskResponseUpdate from './task-response-update';
import TaskResponseDeleteDialog from './task-response-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TaskResponseUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TaskResponseUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TaskResponseDetail} />
      <ErrorBoundaryRoute path={match.url} component={TaskResponse} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TaskResponseDeleteDialog} />
  </>
);

export default Routes;
