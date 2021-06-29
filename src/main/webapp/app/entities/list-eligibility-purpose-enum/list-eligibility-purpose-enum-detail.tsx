import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './list-eligibility-purpose-enum.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ListEligibilityPurposeEnumDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const listEligibilityPurposeEnumEntity = useAppSelector(state => state.listEligibilityPurposeEnum.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="listEligibilityPurposeEnumDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.listEligibilityPurposeEnum.detail.title">ListEligibilityPurposeEnum</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{listEligibilityPurposeEnumEntity.id}</dd>
          <dt>
            <span id="erp">
              <Translate contentKey="hcpNphiesPortalApp.listEligibilityPurposeEnum.erp">Erp</Translate>
            </span>
          </dt>
          <dd>{listEligibilityPurposeEnumEntity.erp}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.listEligibilityPurposeEnum.coverageEligibilityRequest">
              Coverage Eligibility Request
            </Translate>
          </dt>
          <dd>
            {listEligibilityPurposeEnumEntity.coverageEligibilityRequest
              ? listEligibilityPurposeEnumEntity.coverageEligibilityRequest.id
              : ''}
          </dd>
        </dl>
        <Button tag={Link} to="/list-eligibility-purpose-enum" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/list-eligibility-purpose-enum/${listEligibilityPurposeEnumEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ListEligibilityPurposeEnumDetail;
