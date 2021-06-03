import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './list-eligibility-purpose-enum.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IListEligibilityPurposeEnumDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ListEligibilityPurposeEnumDetail = (props: IListEligibilityPurposeEnumDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { listEligibilityPurposeEnumEntity } = props;
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

const mapStateToProps = ({ listEligibilityPurposeEnum }: IRootState) => ({
  listEligibilityPurposeEnumEntity: listEligibilityPurposeEnum.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ListEligibilityPurposeEnumDetail);
