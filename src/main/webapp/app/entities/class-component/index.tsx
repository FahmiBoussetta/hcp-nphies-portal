import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ClassComponent from './class-component';
import ClassComponentDetail from './class-component-detail';
import ClassComponentUpdate from './class-component-update';
import ClassComponentDeleteDialog from './class-component-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ClassComponentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ClassComponentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ClassComponentDetail} />
      <ErrorBoundaryRoute path={match.url} component={ClassComponent} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ClassComponentDeleteDialog} />
  </>
);

export default Routes;
