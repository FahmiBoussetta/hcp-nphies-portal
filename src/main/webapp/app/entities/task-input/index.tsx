import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TaskInput from './task-input';
import TaskInputDetail from './task-input-detail';
import TaskInputUpdate from './task-input-update';
import TaskInputDeleteDialog from './task-input-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TaskInputUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TaskInputUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TaskInputDetail} />
      <ErrorBoundaryRoute path={match.url} component={TaskInput} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TaskInputDeleteDialog} />
  </>
);

export default Routes;
