import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './cost-to-beneficiary-component.reducer';
import { ICostToBeneficiaryComponent } from 'app/shared/model/cost-to-beneficiary-component.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CostToBeneficiaryComponent = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const costToBeneficiaryComponentList = useAppSelector(state => state.costToBeneficiaryComponent.entities);
  const loading = useAppSelector(state => state.costToBeneficiaryComponent.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="cost-to-beneficiary-component-heading" data-cy="CostToBeneficiaryComponentHeading">
        <Translate contentKey="hcpNphiesPortalApp.costToBeneficiaryComponent.home.title">Cost To Beneficiary Components</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hcpNphiesPortalApp.costToBeneficiaryComponent.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hcpNphiesPortalApp.costToBeneficiaryComponent.home.createLabel">
              Create new Cost To Beneficiary Component
            </Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {costToBeneficiaryComponentList && costToBeneficiaryComponentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.costToBeneficiaryComponent.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.costToBeneficiaryComponent.type">Type</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.costToBeneficiaryComponent.isMoney">Is Money</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.costToBeneficiaryComponent.value">Value</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.costToBeneficiaryComponent.coverage">Coverage</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {costToBeneficiaryComponentList.map((costToBeneficiaryComponent, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${costToBeneficiaryComponent.id}`} color="link" size="sm">
                      {costToBeneficiaryComponent.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`hcpNphiesPortalApp.CostToBeneficiaryTypeEnum.${costToBeneficiaryComponent.type}`} />
                  </td>
                  <td>{costToBeneficiaryComponent.isMoney ? 'true' : 'false'}</td>
                  <td>{costToBeneficiaryComponent.value}</td>
                  <td>
                    {costToBeneficiaryComponent.coverage ? (
                      <Link to={`coverage/${costToBeneficiaryComponent.coverage.id}`}>{costToBeneficiaryComponent.coverage.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`${match.url}/${costToBeneficiaryComponent.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${costToBeneficiaryComponent.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${costToBeneficiaryComponent.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="hcpNphiesPortalApp.costToBeneficiaryComponent.home.notFound">
                No Cost To Beneficiary Components found
              </Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default CostToBeneficiaryComponent;
