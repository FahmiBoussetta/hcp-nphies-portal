import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Acknowledgement from './acknowledgement';
import AcknowledgementDetail from './acknowledgement-detail';
import AcknowledgementUpdate from './acknowledgement-update';
import AcknowledgementDeleteDialog from './acknowledgement-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AcknowledgementUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AcknowledgementUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AcknowledgementDetail} />
      <ErrorBoundaryRoute path={match.url} component={Acknowledgement} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AcknowledgementDeleteDialog} />
  </>
);

export default Routes;
