import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CovEliRespErrorMessages from './cov-eli-resp-error-messages';
import CovEliRespErrorMessagesDetail from './cov-eli-resp-error-messages-detail';
import CovEliRespErrorMessagesUpdate from './cov-eli-resp-error-messages-update';
import CovEliRespErrorMessagesDeleteDialog from './cov-eli-resp-error-messages-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CovEliRespErrorMessagesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CovEliRespErrorMessagesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CovEliRespErrorMessagesDetail} />
      <ErrorBoundaryRoute path={match.url} component={CovEliRespErrorMessages} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CovEliRespErrorMessagesDeleteDialog} />
  </>
);

export default Routes;
