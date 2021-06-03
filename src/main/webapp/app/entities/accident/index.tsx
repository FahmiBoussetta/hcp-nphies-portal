import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Accident from './accident';
import AccidentDetail from './accident-detail';
import AccidentUpdate from './accident-update';
import AccidentDeleteDialog from './accident-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AccidentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AccidentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AccidentDetail} />
      <ErrorBoundaryRoute path={match.url} component={Accident} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AccidentDeleteDialog} />
  </>
);

export default Routes;
