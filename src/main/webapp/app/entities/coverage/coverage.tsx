import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './coverage.reducer';
import { ICoverage } from 'app/shared/model/coverage.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICoverageProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Coverage = (props: ICoverageProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { coverageList, match, loading } = props;
  return (
    <div>
      <h2 id="coverage-heading" data-cy="CoverageHeading">
        <Translate contentKey="hcpNphiesPortalApp.coverage.home.title">Coverages</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hcpNphiesPortalApp.coverage.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hcpNphiesPortalApp.coverage.home.createLabel">Create new Coverage</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {coverageList && coverageList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.coverage.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.coverage.guid">Guid</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.coverage.forceId">Force Id</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.coverage.coverageType">Coverage Type</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.coverage.subscriberId">Subscriber Id</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.coverage.dependent">Dependent</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.coverage.relationShip">Relation Ship</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.coverage.network">Network</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.coverage.subrogation">Subrogation</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.coverage.subscriberPatient">Subscriber Patient</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.coverage.beneficiary">Beneficiary</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.coverage.payor">Payor</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.coverage.coverageEligibilityRequest">Coverage Eligibility Request</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {coverageList.map((coverage, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${coverage.id}`} color="link" size="sm">
                      {coverage.id}
                    </Button>
                  </td>
                  <td>{coverage.guid}</td>
                  <td>{coverage.forceId}</td>
                  <td>
                    <Translate contentKey={`hcpNphiesPortalApp.CoverageTypeEnum.${coverage.coverageType}`} />
                  </td>
                  <td>{coverage.subscriberId}</td>
                  <td>{coverage.dependent}</td>
                  <td>
                    <Translate contentKey={`hcpNphiesPortalApp.RelationShipEnum.${coverage.relationShip}`} />
                  </td>
                  <td>{coverage.network}</td>
                  <td>{coverage.subrogation ? 'true' : 'false'}</td>
                  <td>
                    {coverage.subscriberPatient ? (
                      <Link to={`patient/${coverage.subscriberPatient.id}`}>{coverage.subscriberPatient.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{coverage.beneficiary ? <Link to={`patient/${coverage.beneficiary.id}`}>{coverage.beneficiary.id}</Link> : ''}</td>
                  <td>{coverage.payor ? <Link to={`organization/${coverage.payor.id}`}>{coverage.payor.id}</Link> : ''}</td>
                  <td>
                    {coverage.coverageEligibilityRequest ? (
                      <Link to={`coverage-eligibility-request/${coverage.coverageEligibilityRequest.id}`}>
                        {coverage.coverageEligibilityRequest.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${coverage.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${coverage.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${coverage.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="hcpNphiesPortalApp.coverage.home.notFound">No Coverages found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ coverage }: IRootState) => ({
  coverageList: coverage.entities,
  loading: coverage.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Coverage);
