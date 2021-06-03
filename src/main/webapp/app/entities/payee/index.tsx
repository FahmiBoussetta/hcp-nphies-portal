import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Payee from './payee';
import PayeeDetail from './payee-detail';
import PayeeUpdate from './payee-update';
import PayeeDeleteDialog from './payee-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PayeeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PayeeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PayeeDetail} />
      <ErrorBoundaryRoute path={match.url} component={Payee} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PayeeDeleteDialog} />
  </>
);

export default Routes;
